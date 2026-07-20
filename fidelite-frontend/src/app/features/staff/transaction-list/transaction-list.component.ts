import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TransactionService } from '../../../services/transaction.service';
import { TokenStorageService } from '../../../services/token-storage.service';
import { Transaction } from '../../../core/models/transaction.model';

@Component({
  selector: 'app-transaction-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transaction-list.component.html',
  styleUrl: './transaction-list.component.scss'
})
export class TransactionListComponent implements OnInit {
  private readonly _transactionService = inject(TransactionService);
  private readonly _tokenStorage = inject(TokenStorageService);
  private readonly _cdr = inject(ChangeDetectorRef);

  protected transactions: Transaction[] = [];
  protected totalElements = 0;
  protected searchTerm = '';
  protected typeFilter: string = '';

  ngOnInit(): void {
    const merchantId = this._tokenStorage.getMerchantId();
    if (!merchantId) return;
    this._transactionService.getByMerchant(merchantId).subscribe({
      next: (page) => {
        this.transactions = page.content;
        this.totalElements = page.totalElements;
        this._cdr.detectChanges();
      }
    });
  }

  protected get filtered(): Transaction[] {
    let list = this.transactions;
    if (this.typeFilter) list = list.filter(t => t.type === this.typeFilter);
    const term = this.searchTerm.trim().toLowerCase();
    if (term) {
      list = list.filter(t =>
        t.customerName?.toLowerCase().includes(term) ||
        t.cardBarcode?.toLowerCase().includes(term) ||
        t.productNames?.some(p => p.toLowerCase().includes(term))
      );
    }
    return list;
  }

  protected formatDate(dt: string): string {
    if (!dt) return '—';
    return new Date(dt).toLocaleDateString('fr-FR', { day: '2-digit', month: 'short', year: 'numeric', hour: '2-digit', minute: '2-digit' });
  }

  protected pointsLabel(tx: Transaction): string {
    return tx.points > 0 ? `+${tx.points}` : String(tx.points);
  }
}
