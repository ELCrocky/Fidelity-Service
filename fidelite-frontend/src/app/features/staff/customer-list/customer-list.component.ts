import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
// import { CustomerService } from '../../../services/customer.service';
// import { LoyaltyCardService } from '../../../services/loyalty-card.service';
// import { TransactionService } from '../../../services/transaction.service';
// import { TokenStorageService } from '../../../services/token-storage.service';
// import { Customer } from '../../../core/models/customer.model';

// Searchable customer table: name, email, card barcode, points balance, tier badge.
// Search is client-side filter on the already-loaded list (merchant list is bounded).
// Row click → show a side panel or expand row with the customer's transaction history.
// Transaction history uses TransactionService.getByCard(cardId, pageable).
@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './customer-list.component.html',
  styleUrl: './customer-list.component.scss'
})
export class CustomerListComponent implements OnInit {

  searchTerm = '';
  selectedCustomerId: string | null = null; // drives the detail panel

  ngOnInit(): void {
    // Load all customers for the merchant via CustomerService.getByMerchant(merchantId)
  }
}
