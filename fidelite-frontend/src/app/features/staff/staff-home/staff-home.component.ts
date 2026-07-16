import { Component } from '@angular/core';

// Staff dashboard overview with KPI cards and recent transactions.
@Component({
  selector: 'app-staff-home',
  standalone: true,
  imports: [],
  templateUrl: './staff-home.component.html',
  styleUrl: './staff-home.component.scss'
})
export class StaffHomeComponent {
  // TODO: replace with real API calls via CustomerService + TransactionService (forkJoin)
  protected readonly stats = [
    { label: 'Clients total', value: '1 284', delta: '+42 ce mois', tone: 'blue', icon: 'users' },
    { label: 'Cartes actives', value: '1 047', delta: '81% du total', tone: 'green', icon: 'card' },
    { label: 'Points émis', value: '48 320', delta: 'Ce mois-ci', tone: 'orange', icon: 'trend' },
    { label: 'Points échangés', value: '12 740', delta: 'Ce mois-ci', tone: 'purple', icon: 'refresh' }
  ];

  // TODO: replace with TransactionService.getRecent(merchantId, {page:0, size:10})
  protected readonly transactions = [
    { date: '2024-07-11', client: 'Sophie Leclerc', product: 'Dessert du Jour', type: 'EARN', points: '+45' },
    { date: '2024-07-10', client: 'Marie Dupont', product: 'Menu Déjeuner', type: 'EARN', points: '+120' },
    { date: '2024-07-10', client: 'Céline Moreau', product: 'Plateau Fromages', type: 'EARN', points: '+80' },
    { date: '2024-07-09', client: 'Jean-Pierre Martin', product: 'Burger Classic', type: 'EARN', points: '+60' },
    { date: '2024-07-08', client: 'Marie Dupont', product: 'Café Offert', type: 'REDEEM', points: '-200' },
    { date: '2024-07-07', client: 'Isabelle Bernard', product: 'Repas Gratuit', type: 'REDEEM', points: '-500' },
    { date: '2024-07-06', client: 'Isabelle Bernard', product: 'Salade César', type: 'EARN', points: '+150' },
    { date: '2024-07-03', client: 'Jean-Pierre Martin', product: 'Menu Famille', type: 'EARN', points: '+90' }
  ];
}
