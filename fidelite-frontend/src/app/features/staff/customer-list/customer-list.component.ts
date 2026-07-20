import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import { CustomerService } from '../../../services/customer.service';
import { TransactionService } from '../../../services/transaction.service';
import { TokenStorageService } from '../../../services/token-storage.service';

interface StaffCustomer {
  id: string;
  cardId: string | null;
  initials: string;
  name: string;
  email: string;
  barcode: string;
  points: number;
  tier: string;
  status: string;
  dateNaissance: string;
}

interface HistoryItem {
  label: string;
  date: string;
  points: string;
}

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './customer-list.component.html',
  styleUrl: './customer-list.component.scss'
})
export class CustomerListComponent implements OnInit {
  private readonly _customerService = inject(CustomerService);
  private readonly _transactionService = inject(TransactionService);
  private readonly _tokenStorage = inject(TokenStorageService);
  private readonly _cdr = inject(ChangeDetectorRef);

  protected searchTerm = '';
  protected customers: StaffCustomer[] = [];
  protected selectedCustomer: StaffCustomer | null = null;
  protected selectedHistory: HistoryItem[] = [];

  ngOnInit(): void {
    const merchantId = this._tokenStorage.getMerchantId();
    if (!merchantId) return;

    this._customerService.getByMerchant(merchantId).pipe(
      switchMap(page => {
        if (page.content.length === 0) return of([]);
        return forkJoin(
          page.content.map(c =>
            this._customerService.getCardsByCustomer(c.id).pipe()
          )
        ).pipe(
          switchMap(cardLists =>
            of(page.content.map((c, i) => {
              const card = cardLists[i]?.[0] ?? null;
              return {
                id: c.id,
                cardId: card?.id ?? null,
                initials: (c.prenom?.[0] ?? '') + (c.nom?.[0] ?? ''),
                name: `${c.prenom} ${c.nom}`,
                email: c.email,
                barcode: card?.barcodeEan13 ?? '—',
                points: card?.pointsBalance ?? 0,
                tier: '—',
                status: card ? (card.status === 'ACTIVE' ? 'Actif' : card.status) : '—',
                dateNaissance: c.dateNaissance ?? '—',
              } as StaffCustomer;
            }))
          )
        );
      })
    ).subscribe(customers => {
      this.customers = customers;
      this._cdr.detectChanges();
      if (customers.length > 0) this.selectCustomer(customers[0]);
    });
  }

  protected get filteredCustomers(): StaffCustomer[] {
    const term = this.searchTerm.trim().toLowerCase();
    if (!term) return this.customers;
    return this.customers.filter(c =>
      [c.name, c.email, c.barcode].some(v => v.toLowerCase().includes(term))
    );
  }

  protected selectCustomer(customer: StaffCustomer): void {
    this.selectedCustomer = customer;
    this.selectedHistory = [];
    if (!customer.cardId) return;
    this._transactionService.getByCard(customer.cardId).subscribe({
      next: (page) => {
        this.selectedHistory = page.content.map(tx => ({
          label: tx.type,
          date: tx.createdAt?.substring(0, 10) ?? '',
          points: tx.points > 0 ? `+${tx.points}` : String(tx.points)
        }));
        this._cdr.detectChanges();
      }
    });
  }
}
