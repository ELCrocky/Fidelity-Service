import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import { ProductService } from '../../../services/product.service';
// import { TokenStorageService } from '../../../services/token-storage.service';
// import { Product, ProductRequest } from '../../../core/models/product.model';

// CRUD table for merchant products.
// "Ajouter" opens an inline form (showForm = true) to create a new product.
// Edit icon pre-fills the form with selected product data (editingId set).
// Delete calls ProductService.delete(id) then reloads the list.
// promotion is a boolean toggle — render as a checkbox or toggle switch in the table.
@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit {

  showForm = false;
  editingId: number | null = null;

  ngOnInit(): void {
    // Load products via ProductService.getByMerchant(merchantId)
  }
}
