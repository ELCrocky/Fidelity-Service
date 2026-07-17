import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { ThemeService } from '../../../services/theme.service';

@Component({
  selector: 'app-staff-shell',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './staff-shell.component.html',
  styleUrl: './staff-shell.component.scss'
})
export class StaffShellComponent {
  private readonly _authService = inject(AuthService);
  private readonly _router = inject(Router);
  protected readonly themeService = inject(ThemeService);

  protected readonly navItems = [
    { label: 'Tableau de bord', route: '/staff', icon: 'grid' },
    { label: 'Clients', route: '/staff/customers', icon: 'users' },
    { label: 'Produits', route: '/staff/products', icon: 'box' },
    { label: 'Récompenses', route: '/staff/rewards', icon: 'gift' },
    { label: 'Niveaux', route: '/staff/tiers', icon: 'layers' }
  ];

  protected readonly operator = {
    name: 'Manager',
    email: 'admin@erolis.fr',
    initials: 'M'
  };

  protected logout(): void {
    this._authService.logout();
    this._router.navigate(['/login']);
  }
}
