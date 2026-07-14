import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-clearing-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './clearing-login.component.html',
  styleUrl: './clearing-login.component.scss'
})
export class ClearingLoginComponent {}
