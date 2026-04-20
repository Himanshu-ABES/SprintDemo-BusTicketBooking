import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './home.component.html'
})
export class HomeComponent {

  searchForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router
  ) {
    this.searchForm = this.fb.group({
      src: ['', [Validators.required]],
      dest: ['', [Validators.required]],
      date: ['', [Validators.required]]
    });
  }

  get f() {
    return this.searchForm.controls;
  }

  onSearch(): void {
    if (this.searchForm.invalid) {
      this.searchForm.markAllAsTouched();
      return;
    }

    const { src, dest, date } = this.searchForm.value;
    this.router.navigate(['/search'], {
      queryParams: { src, dest, date }
    });
  }
}
