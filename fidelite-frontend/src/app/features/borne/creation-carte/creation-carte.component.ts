import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-creation-carte',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './creation-carte.component.html',
  styleUrl: './creation-carte.component.scss'
})
export class CreationCarteComponent {}
