import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

// Lazy import staff components — keeps the initial bundle small.
// Borne and dashboard routes: add here when their shells are implemented.
const routes: Routes = [
  // Auth
  { path: 'login',          loadComponent: () => import('./features/auth/borne-login/borne-login.component').then(m => m.BorneLoginComponent) },
  { path: 'clearing/login', loadComponent: () => import('./features/auth/clearing-login/clearing-login.component').then(m => m.ClearingLoginComponent) },

  // Kiosk (STAFF + MERCHANT_ADMIN)
  {
    path: 'borne',
    loadComponent: () => import('./features/borne/borne-shell/borne-shell.component').then(m => m.BorneShellComponent),
    canActivate: [authGuard, roleGuard],
    data: { roles: ['STAFF', 'MERCHANT_ADMIN'] },
    children: [
      { path: 'scan',     loadComponent: () => import('./features/borne/scan-carte/scan-carte.component').then(m => m.ScanCarteComponent) },
      { path: 'profil',   loadComponent: () => import('./features/borne/profil-client/profil-client.component').then(m => m.ProfilClientComponent) },
      { path: 'creation', loadComponent: () => import('./features/borne/creation-carte/creation-carte.component').then(m => m.CreationCarteComponent) },
      { path: 'proposition', loadComponent: () => import('./features/borne/proposition-carte/proposition-carte.component').then(m => m.PropositionCarteComponent) },
      { path: 'promotions',  loadComponent: () => import('./features/borne/promotions/promotions.component').then(m => m.PromotionsComponent) },
    ]
  },

  // Staff admin panel (MERCHANT_ADMIN only)
  {
    path: 'staff',
    loadComponent: () => import('./features/staff/staff-shell/staff-shell.component').then(m => m.StaffShellComponent),
    canActivate: [authGuard, roleGuard],
    data: { roles: ['MERCHANT_ADMIN'] },
    children: [
      { path: '',           loadComponent: () => import('./features/staff/staff-home/staff-home.component').then(m => m.StaffHomeComponent) },
      { path: 'customers',  loadComponent: () => import('./features/staff/customer-list/customer-list.component').then(m => m.CustomerListComponent) },
      { path: 'products',   loadComponent: () => import('./features/staff/product-list/product-list.component').then(m => m.ProductListComponent) },
      { path: 'rewards',    loadComponent: () => import('./features/staff/reward-list/reward-list.component').then(m => m.RewardListComponent) },
      { path: 'tiers',      loadComponent: () => import('./features/staff/tier-list/tier-list.component').then(m => m.TierListComponent) },
    ]
  },

  // Clearing dashboard (CLEARING only)
  {
    path: 'dashboard',
    loadComponent: () => import('./features/dashboard/dashboard-shell/dashboard-shell.component').then(m => m.DashboardShellComponent),
    canActivate: [authGuard, roleGuard],
    data: { roles: ['CLEARING'] },
    children: [
      { path: '',           loadComponent: () => import('./features/dashboard/dashboard-home/dashboard-home.component').then(m => m.DashboardHomeComponent) },
      { path: 'pools',      loadComponent: () => import('./features/dashboard/pool-list/pool-list.component').then(m => m.PoolListComponent) },
      { path: 'pools/:id',  loadComponent: () => import('./features/dashboard/pool-detail/pool-detail.component').then(m => m.PoolDetailComponent) },
      { path: 'settlements',    loadComponent: () => import('./features/dashboard/settlement-list/settlement-list.component').then(m => m.SettlementListComponent) },
      { path: 'settlements/:id',loadComponent: () => import('./features/dashboard/settlement-detail/settlement-detail.component').then(m => m.SettlementDetailComponent) },
    ]
  },

  // Default redirect — borne-login handles role-based redirect after login
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
