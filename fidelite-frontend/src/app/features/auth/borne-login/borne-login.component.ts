import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-borne-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './borne-login.component.html',
  styleUrl: './borne-login.component.scss'
})
export class BorneLoginComponent {

  private readonly _fb = inject(NonNullableFormBuilder);
  private readonly _authService = inject(AuthService);
  private readonly _router = inject(Router);


  protected readonly loginForm = this._fb.group({
    email: ["", [Validators.required, Validators.email]],
    password: ["", Validators.required]
  })
  errorMessage: string | null = null;
  protected isLoading = false;


protected onSubmit(): void {
  if (this.loginForm.invalid) {
    return;
  }

  this.isLoading = true;
  this.errorMessage = null;

  this._authService.loginBorne(this.loginForm.getRawValue()).subscribe({
    next: (res) => {
      this.isLoading = false;
      if (res.role === 'MERCHANT_ADMIN') {
        this._router.navigate(['/staff']);
      } else {
        this._router.navigate(['/borne/scan']);
      }
    },
    error: () => {
      this.isLoading = false;
      this.errorMessage = 'Email ou mot de passe incorrect';
    }
  });
}
}
