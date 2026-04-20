import { Injectable, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest, LoginResponse, RegisterRequest } from '../models/auth.model';
import { Customer } from '../models/customer.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8080/api/customers';
  private platformId = inject(PLATFORM_ID);

  constructor(private http: HttpClient) { }

  private get isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }

  register(data: RegisterRequest): Observable<Customer> {
    return this.http.post<Customer>(`${this.baseUrl}/register`, data);
  }

  login(data: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, data);
  }

  saveSession(response: LoginResponse): void {
    if (this.isBrowser) {
      localStorage.setItem('token', response.token);
      localStorage.setItem('custId', response.custId.toString());
      localStorage.setItem('custName', response.custName);
    }
  }

  logout(): void {
    if (this.isBrowser) {
      localStorage.removeItem('token');
      localStorage.removeItem('custId');
      localStorage.removeItem('custName');
    }
  }

  isLoggedIn(): boolean {
    if (!this.isBrowser) return false;
    return !!localStorage.getItem('token');
  }

  getToken(): string | null {
    if (!this.isBrowser) return null;
    return localStorage.getItem('token');
  }

  getCustId(): number {
    if (!this.isBrowser) return 0;
    return Number(localStorage.getItem('custId'));
  }

  getCustName(): string {
    if (!this.isBrowser) return '';
    return localStorage.getItem('custName') || '';
  }
}
