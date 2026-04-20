import { Component, OnInit, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { RouteSchedule } from '../../models/schedule.model';
import { AuthService } from '../../services/auth.service';

interface Seat {
  label: string;
  row: string;
  col: number;
  status: 'available' | 'taken' | 'selected';
}

interface PassengerForm {
  seatNo: string;
  passengerName: string;
  passengerAge: number | null;
}

@Component({
  selector: 'app-book',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './book.component.html'
})
export class BookComponent implements OnInit {

  schedule: RouteSchedule | null = null;
  seats: Seat[] = [];
  seatRows: Seat[][] = [];
  passengers: PassengerForm[] = [];

  isLoading: boolean = false;
  isBooking: boolean = false;
  errorMessage: string = '';
  bookingId: number | null = null;
  bookingSuccess: boolean = false;

  private apiUrl = 'http://localhost:8080/api';
  private platformId = inject(PLATFORM_ID);

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    const scheduleId = Number(this.route.snapshot.paramMap.get('scheduleId'));

    // Try to read schedule from navigation state
    if (isPlatformBrowser(this.platformId)) {
      const navState = history.state?.schedule;
      if (navState) {
        this.schedule = navState;
        this.buildSeatMap();
        return;
      }
    }

    // Fallback: fetch from API
    if (scheduleId) {
      this.loadSchedule(scheduleId);
    }
  }

  loadSchedule(scheduleId: number): void {
    this.isLoading = true;
    this.http.get<RouteSchedule>(`${this.apiUrl}/schedules/${scheduleId}`).subscribe({
      next: (data) => {
        this.schedule = data;
        this.isLoading = false;
        this.buildSeatMap();
      },
      error: () => {
        this.isLoading = false;
        this.errorMessage = 'Failed to load schedule details.';
      }
    });
  }

  buildSeatMap(): void {
    if (!this.schedule) return;

    const total = this.schedule.totSeats;
    const avl = this.schedule.avlSeats;
    const taken = total - avl;
    const seatsPerRow = 7;

    this.seats = [];

    for (let i = 0; i < total; i++) {
      const rowIndex = Math.floor(i / seatsPerRow);
      const colIndex = (i % seatsPerRow) + 1;
      const rowLabel = String.fromCharCode(65 + rowIndex); // A, B, C...
      const label = `${rowLabel}${colIndex}`;

      // Last N seats are taken (where N = total - available)
      const isTaken = i >= (total - taken);

      this.seats.push({
        label,
        row: rowLabel,
        col: colIndex,
        status: isTaken ? 'taken' : 'available'
      });
    }

    // Group into rows
    this.seatRows = [];
    for (let i = 0; i < this.seats.length; i += seatsPerRow) {
      this.seatRows.push(this.seats.slice(i, i + seatsPerRow));
    }
  }

  toggleSeat(seat: Seat): void {
    if (seat.status === 'taken') return;

    if (seat.status === 'selected') {
      seat.status = 'available';
      this.passengers = this.passengers.filter(p => p.seatNo !== seat.label);
    } else {
      seat.status = 'selected';
      this.passengers.push({
        seatNo: seat.label,
        passengerName: '',
        passengerAge: null
      });
    }
  }

  get selectedSeats(): Seat[] {
    return this.seats.filter(s => s.status === 'selected');
  }

  get selectedLabels(): string {
    return this.selectedSeats.map(s => s.label).join(', ');
  }

  get isFormValid(): boolean {
    return this.passengers.length > 0 &&
           this.passengers.every(p => p.passengerName.trim() !== '' && p.passengerAge !== null && p.passengerAge > 0);
  }

  confirmBooking(): void {
    if (!this.isFormValid || !this.schedule) return;

    this.isBooking = true;
    this.errorMessage = '';

    const bookingRequest = {
      customerId: this.authService.getCustId(),
      scheduleId: this.schedule.id,
      passengers: this.passengers.map(p => ({
        passengerName: p.passengerName,
        passengerAge: p.passengerAge,
        seatNo: p.seatNo
      }))
    };

    this.http.post<any>(`${this.apiUrl}/bookings`, bookingRequest).subscribe({
      next: (response) => {
        this.isBooking = false;
        this.bookingId = response.id;
        this.bookingSuccess = true;
      },
      error: (err) => {
        this.isBooking = false;
        this.errorMessage = err.error?.message || 'Booking failed. Please try again.';
      }
    });
  }
}
