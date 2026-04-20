import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { RouteSchedule } from '../../models/schedule.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './search.component.html'
})
export class SearchComponent implements OnInit {

  schedules: RouteSchedule[] = [];
  isLoading: boolean = false;
  errorMessage: string = '';
  src: string = '';
  dest: string = '';
  date: string = '';

  private apiUrl = 'http://localhost:8080/api/schedules/search';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.src = params['src'] || '';
      this.dest = params['dest'] || '';
      this.date = params['date'] || '';

      if (this.src && this.dest && this.date) {
        this.searchSchedules();
      }
    });
  }

  searchSchedules(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.http.get<RouteSchedule[]>(this.apiUrl, {
      params: { src: this.src, dest: this.dest, date: this.date }
    }).subscribe({
      next: (data) => {
        this.schedules = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = 'Failed to fetch schedules. Please try again.';
      }
    });
  }

  bookNow(schedule: RouteSchedule): void {
    this.router.navigate(['/book', schedule.id], {
      state: { schedule }
    });
  }

  getAvailabilityClass(avl: number): string {
    if (avl > 10) return 'ios-badge-success';
    if (avl > 0) return 'ios-badge-amber';
    return 'ios-badge-danger';
  }
}
