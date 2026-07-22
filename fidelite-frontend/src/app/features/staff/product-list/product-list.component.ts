import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { FormsModule, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Product } from '../../../core/models/product.model';
import { ProductService } from '../../../services/product.service';
import { TransactionService } from '../../../services/transaction.service';
import { TokenStorageService } from '../../../services/token-storage.service';

interface SaleItem {
  date: string;
  productName: string;
  customerName: string;
}

interface SaleGroup {
  productName: string;
  count: number;
}

// Product management: table with promotion toggle, create/edit modal backed by ProductService.
@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit {
  private readonly _fb = inject(NonNullableFormBuilder);
  private readonly _productService = inject(ProductService);
  private readonly _transactionService = inject(TransactionService);
  private readonly _tokenStorage = inject(TokenStorageService);
  private readonly _cdr = inject(ChangeDetectorRef);

  protected products: Product[] = [];
  protected searchTerm = '';
  protected salesTimeline: SaleItem[] = [];
  protected salesGroups: SaleGroup[] = [];

  protected currentPage = 0;
  protected readonly pageSize = 15;
  protected salesPage = 0;

  protected get filteredProducts(): Product[] {
    const t = this.searchTerm.trim().toLowerCase();
    if (!t) return this.products;
    return this.products.filter(p => p.name.toLowerCase().includes(t) || p.productType.toLowerCase().includes(t));
  }
  protected get totalPages(): number { return Math.max(1, Math.ceil(this.filteredProducts.length / this.pageSize)); }
  protected get pageStart(): number { return this.currentPage * this.pageSize + 1; }
  protected get pageEnd(): number { return Math.min(this.filteredProducts.length, (this.currentPage + 1) * this.pageSize); }
  protected get pagedProducts(): Product[] {
    const s = this.currentPage * this.pageSize;
    return this.filteredProducts.slice(s, s + this.pageSize);
  }
  protected get salesTotalPages(): number { return Math.max(1, Math.ceil(this.salesTimeline.length / this.pageSize)); }
  protected get salesPageStart(): number { return this.salesPage * this.pageSize + 1; }
  protected get salesPageEnd(): number { return Math.min(this.salesTimeline.length, (this.salesPage + 1) * this.pageSize); }
  protected get pagedSales(): SaleItem[] {
    const s = this.salesPage * this.pageSize;
    return this.salesTimeline.slice(s, s + this.pageSize);
  }
  protected get pageNumbers(): number[] { return Array.from({ length: this.totalPages }, (_, i) => i); }
  protected get salesPageNumbers(): number[] { return Array.from({ length: this.salesTotalPages }, (_, i) => i); }
  protected prevPage(): void { if (this.currentPage > 0) this.currentPage--; }
  protected nextPage(): void { if (this.currentPage < this.totalPages - 1) this.currentPage++; }
  protected goToPage(p: number): void { this.currentPage = p; }
  protected prevSalesPage(): void { if (this.salesPage > 0) this.salesPage--; }
  protected nextSalesPage(): void { if (this.salesPage < this.salesTotalPages - 1) this.salesPage++; }
  protected goToSalesPage(p: number): void { this.salesPage = p; }
  protected onSearch(term: string): void { this.searchTerm = term; this.currentPage = 0; }

  protected formatDate(dt: string): string {
    if (!dt) return '—';
    return new Date(dt).toLocaleDateString('fr-FR', { day: '2-digit', month: 'short', year: 'numeric', hour: '2-digit', minute: '2-digit' });
  }

  protected showForm = false;
  protected editingId: number | null = null;
  protected errorMessage: string | null = null;

  protected readonly productForm = this._fb.group({
    name: ['', [Validators.required]],
    productType: ['REPAS' as 'REPAS' | 'DESSERT' | 'BOISSON', [Validators.required]],
    productPoint: [0, [Validators.required, Validators.min(1)]],
    promotion: [false]
  });

  protected readonly labels = {
    REPAS: 'REPAS',
    DESSERT: 'DESSERT',
    BOISSON: 'BOISSON'
  } as const;

  ngOnInit(): void {
    const merchantId = this._tokenStorage.getMerchantId();
    if (!merchantId) return;
    forkJoin({
      products: this._productService.getByMerchant(merchantId),
      txs: this._transactionService.getByMerchant(merchantId, 0, 500),
    }).subscribe({
      next: ({ products, txs }) => {
        this.products = products;
        const timeline: SaleItem[] = [];
        for (const tx of txs.content) {
          if (tx.type !== 'EARN' || !tx.productNames?.length) continue;
          for (const name of tx.productNames) {
            timeline.push({ date: tx.createdAt, productName: name, customerName: tx.customerName ?? '—' });
          }
        }
        timeline.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());
        this.salesTimeline = timeline;

        const counts: Record<string, number> = {};
        for (const s of timeline) counts[s.productName] = (counts[s.productName] ?? 0) + 1;
        this.salesGroups = Object.entries(counts)
          .map(([productName, count]) => ({ productName, count }))
          .sort((a, b) => b.count - a.count);

        this._cdr.detectChanges();
      },
      error: () => this.errorMessage = 'Impossible de charger les produits.'
    });
  }

  protected togglePromotion(product: Product): void {
    const newValue = !product.promotion;
    const request = {
      name: product.name,
      productType: product.productType,
      productPoint: product.productPoint,
      promotion: newValue,
      merchantId: this._tokenStorage.getMerchantId() ?? ''
    };
    this._productService.update(product.id, request).subscribe({
      next: (updated) => {
        product.promotion = updated.promotion;
        this._cdr.detectChanges();
      }
    });
  }

  protected openForm(product?: Product): void {
    this.showForm = true;
    this.errorMessage = null;
    if (product) {
      this.editingId = product.id;
      this.productForm.setValue({
        name: product.name,
        productType: product.productType,
        productPoint: product.productPoint,
        promotion: product.promotion
      });
    } else {
      this.editingId = null;
      this.productForm.reset({ productType: 'REPAS', productPoint: 0, promotion: false });
    }
  }

  protected closeForm(): void {
    this.showForm = false;
    this.editingId = null;
    this.errorMessage = null;
    this.productForm.reset();
  }

  protected onSubmit(): void {
    if (this.productForm.invalid) return;
    const value = this.productForm.getRawValue();
    const request = { ...value, merchantId: this._tokenStorage.getMerchantId() ?? '' };

    if (this.editingId !== null) {
      this._productService.update(this.editingId, request).subscribe({
        next: (updated) => {
          const idx = this.products.findIndex(p => p.id === this.editingId);
          if (idx !== -1) this.products[idx] = updated;
          this.closeForm();
        },
        error: () => this.errorMessage = 'Une erreur est survenue lors de la modification.'
      });
    } else {
      this._productService.create(request).subscribe({
        next: (created) => {
          this.products = [...this.products, created];
          this.closeForm();
        },
        error: () => this.errorMessage = 'Une erreur est survenue lors de la création.'
      });
    }
  }
}
