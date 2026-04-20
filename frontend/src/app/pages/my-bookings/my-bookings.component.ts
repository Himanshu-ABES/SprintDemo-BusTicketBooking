import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { BusBooking } from '../../models/booking.model';

@Component({
  selector: 'app-my-bookings',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './my-bookings.component.html'
})
export class MyBookingsComponent implements OnInit {

  bookings: BusBooking[] = [];
  isLoading: boolean = false;
  errorMessage: string = '';

  private apiUrl = 'http://localhost:8080/api/bookings';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadBookings();
  }

  loadBookings(): void {
    this.isLoading = true;
    this.errorMessage = '';

    const custId = this.authService.getCustId();
    this.http.get<BusBooking[]>(`${this.apiUrl}/customer/${custId}`).subscribe({
      next: (data) => {
        this.bookings = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = 'Failed to load bookings. Please try again.';
      }
    });
  }

  getStatusClass(status: string): string {
    switch (status?.toUpperCase()) {
      case 'CONFIRMED': return 'ios-badge-success';
      case 'CANCELLED': return 'ios-badge-danger';
      default: return 'ios-badge-muted';
    }
  }
}
