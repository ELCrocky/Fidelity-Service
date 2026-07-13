import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/auth.service';

// Shell layout: navy sidebar + <router-outlet> for child routes.
// Nav links: /staff (home), /staff/customers, /staff/products, /staff/rewards, /staff/tiers
// Logout button calls AuthService.logout() then navigates to /login.
@Component({
  selector: 'app-staff-shell',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './staff-shell.component.html',
  styleUrl: './staff-shell.component.scss'
})
export class StaffShellComponent {

  constructor(private authService: AuthService, private router: Router) {}

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
