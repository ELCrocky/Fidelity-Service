import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
// import { CustomerService } from '../../../services/customer.service';
// import { TransactionService } from '../../../services/transaction.service';
// import { TokenStorageService } from '../../../services/token-storage.service';

// Dashboard home: 4 stat cards + recent transactions table.
// Stat cards: total customers, active cards, points issued this month, points redeemed this month.
// merchantId must be read from TokenStorageService (it was stored at login via storeSession).
// All API calls are scoped to the merchant — pass merchantId as query param.
@Component({
  selector: 'app-staff-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './staff-home.component.html',
  styleUrl: './staff-home.component.scss'
})
export class StaffHomeComponent implements OnInit {

  ngOnInit(): void {
    // 1. Get merchantId from TokenStorageService
    // 2. Load customer count, transaction stats in parallel (forkJoin)
    // 3. Load recent transactions (first page, page size 10)
  }
}
