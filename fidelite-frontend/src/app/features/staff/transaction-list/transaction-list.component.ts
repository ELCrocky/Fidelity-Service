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

  protected currentPage = 0;
  protected readonly pageSize = 15;

  protected get totalPages(): number { return Math.max(1, Math.ceil(this.filtered.length / this.pageSize)); }
  protected get pageStart(): number { return this.currentPage * this.pageSize + 1; }
  protected get pageEnd(): number { return Math.min(this.filtered.length, (this.currentPage + 1) * this.pageSize); }
  protected get paged(): Transaction[] {
    const s = this.currentPage * this.pageSize;
    return this.filtered.slice(s, s + this.pageSize);
  }
  protected get productGroups(): { name: string; count: number }[] {
    const counts: Record<string, number> = {};
    for (const tx of this.transactions) {
      if (tx.type !== 'EARN' || !tx.productNames?.length) continue;
      for (const name of tx.productNames) counts[name] = (counts[name] ?? 0) + 1;
    }
    return Object.entries(counts).map(([name, count]) => ({ name, count })).sort((a, b) => b.count - a.count);
  }

  protected get clientGroups(): { name: string; count: number }[] {
    const counts: Record<string, number> = {};
    for (const tx of this.transactions) {
      const name = tx.customerName || '—';
      counts[name] = (counts[name] ?? 0) + 1;
    }
    return Object.entries(counts).map(([name, count]) => ({ name, count })).sort((a, b) => b.count - a.count);
  }

  protected get pageNumbers(): number[] { return Array.from({ length: this.totalPages }, (_, i) => i); }
  protected prevPage(): void { if (this.currentPage > 0) this.currentPage--; }
  protected nextPage(): void { if (this.currentPage < this.totalPages - 1) this.currentPage++; }
  protected goToPage(p: number): void { this.currentPage = p; }
  protected onSearch(term: string): void { this.searchTerm = term; this.currentPage = 0; }
  protected onTypeFilter(val: string): void { this.typeFilter = val; this.currentPage = 0; }

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
