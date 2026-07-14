import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

// Shell: sidebar nav + <router-outlet> for borne child routes.
@Component({
  selector: 'app-borne-shell',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterModule],
  templateUrl: './borne-shell.component.html',
  styleUrl: './borne-shell.component.scss'
})
export class BorneShellComponent {}
