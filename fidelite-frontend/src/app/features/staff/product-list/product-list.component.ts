import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Product } from '../../../core/models/product.model';
import { ProductService } from '../../../services/product.service';
import { TokenStorageService } from '../../../services/token-storage.service';

// Product management: table with promotion toggle, create/edit modal backed by ProductService.
@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit {
  private readonly _fb = inject(NonNullableFormBuilder);
  private readonly _productService = inject(ProductService);
  private readonly _tokenStorage = inject(TokenStorageService);
  private readonly _cdr = inject(ChangeDetectorRef);

  protected products: Product[] = [];

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
    this._productService.getByMerchant(merchantId).subscribe({
      next: (products) => { this.products = products; this._cdr.detectChanges(); },
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
