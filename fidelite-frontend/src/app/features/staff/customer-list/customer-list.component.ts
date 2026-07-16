import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Customer list with search, selection, and a side detail/history panel.
interface StaffCustomer {
  initials: string;
  name: string;
  email: string;
  barcode: string;
  points: number;
  tier: 'Bronze' | 'Silver' | 'Gold';
  status: 'Actif' | 'Inactif';
  memberSince: string;
  history: Array<{ label: string; date: string; points: string }>;
}
@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './customer-list.component.html',
  styleUrl: './customer-list.component.scss'
})
export class CustomerListComponent {
  protected searchTerm = '';

  // TODO: replace with CustomerService.getByMerchant(merchantId)
  protected readonly customers: StaffCustomer[] = [
    { initials: 'MD', name: 'Marie Dupont', email: 'marie.dupont@email.fr', barcode: 'ERL-2024-001', points: 1240, tier: 'Gold', status: 'Actif', memberSince: '15/03/2023', history: [{ label: 'Menu Déjeuner', date: '2024-07-10', points: '+120' }, { label: 'Café Offert', date: '2024-07-08', points: '-200' }, { label: 'Salade César', date: '2024-07-05', points: '+80' }] },
    { initials: 'JM', name: 'Jean-Pierre Martin', email: 'jp.martin@gmail.com', barcode: 'ERL-2024-002', points: 580, tier: 'Silver', status: 'Actif', memberSince: '02/04/2023', history: [{ label: 'Menu Famille', date: '2024-07-03', points: '+90' }, { label: 'Burger Classic', date: '2024-06-30', points: '+60' }] },
    { initials: 'SL', name: 'Sophie Leclerc', email: 'sophie.l@outlook.fr', barcode: 'ERL-2024-003', points: 210, tier: 'Bronze', status: 'Actif', memberSince: '19/05/2023', history: [{ label: 'Dessert du Jour', date: '2024-07-11', points: '+45' }, { label: 'Café Offert', date: '2024-07-01', points: '-100' }] },
    { initials: 'AR', name: 'Antoine Rousseau', email: 'a.rousseau@free.fr', barcode: 'ERL-2024-004', points: 890, tier: 'Silver', status: 'Inactif', memberSince: '22/01/2023', history: [{ label: 'Plateau Fromages', date: '2024-06-29', points: '+80' }] },
    { initials: 'IB', name: 'Isabelle Bernard', email: 'isa.bernard@sfr.fr', barcode: 'ERL-2024-005', points: 1650, tier: 'Gold', status: 'Actif', memberSince: '15/03/2023', history: [{ label: 'Repas Gratuit', date: '2024-07-07', points: '-500' }, { label: 'Pizza Duo', date: '2024-07-06', points: '+150' }] },
    { initials: 'TP', name: 'Thomas Petit', email: 't.petit@laposte.net', barcode: 'ERL-2024-006', points: 95, tier: 'Bronze', status: 'Actif', memberSince: '28/06/2024', history: [{ label: 'Café Espresso', date: '2024-07-02', points: '+20' }] },
    { initials: 'CM', name: 'Céline Moreau', email: 'c.moreau@orange.fr', barcode: 'ERL-2024-007', points: 440, tier: 'Silver', status: 'Actif', memberSince: '08/02/2024', history: [{ label: 'Plateau Fromages', date: '2024-07-10', points: '+80' }, { label: 'Jus Pressé', date: '2024-07-04', points: '+35' }] }
  ];

  protected selectedCustomer = this.customers[0];

  // Client-side filter used by the search bar.
  protected get filteredCustomers(): StaffCustomer[] {
    const term = this.searchTerm.trim().toLowerCase();
    if (!term) return this.customers;
    return this.customers.filter(c =>
      [c.name, c.email, c.barcode].some(v => v.toLowerCase().includes(term))
    );
  }

  // Updates the right-side detail panel when a row is selected.
  protected selectCustomer(customer: StaffCustomer): void {
    this.selectedCustomer = customer;
  }
}
