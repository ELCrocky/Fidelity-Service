import { Component, inject, OnInit } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Product } from '../../../core/models/product.model';
import { ProductService } from '../../../services/product.service';

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

  // TODO: replace mock data with load() once backend is wired
  protected products: Product[] = [
    { id: 1, name: 'Menu Déjeuner', productType: 'REPAS', productPoint: 120, promotion: false },
    { id: 2, name: 'Menu Famille', productType: 'REPAS', productPoint: 90, promotion: true },
    { id: 3, name: 'Burger Classic', productType: 'REPAS', productPoint: 60, promotion: false },
    { id: 4, name: 'Salade César', productType: 'REPAS', productPoint: 80, promotion: false },
    { id: 5, name: 'Dessert du Jour', productType: 'DESSERT', productPoint: 45, promotion: false },
    { id: 6, name: 'Plateau Fromages', productType: 'DESSERT', productPoint: 110, promotion: true },
    { id: 7, name: 'Tarte Tatin', productType: 'DESSERT', productPoint: 55, promotion: false },
    { id: 8, name: 'Café Espresso', productType: 'BOISSON', productPoint: 20, promotion: false },
    { id: 9, name: 'Jus Pressé', productType: 'BOISSON', productPoint: 35, promotion: true },
    { id: 10, name: 'Smoothie Maison', productType: 'BOISSON', productPoint: 40, promotion: false }
  ];

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
    // TODO: call this._productService.getByMerchant(merchantId) here once backend is wired
  }

  protected togglePromotion(product: Product): void {
    product.promotion = !product.promotion;
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
    // TODO: replace '' with TokenStorageService.getMerchantId() once available
    const request = { ...value, merchantId: '' };

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
