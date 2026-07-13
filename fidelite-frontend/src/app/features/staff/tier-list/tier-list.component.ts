import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import { TierService } from '../../../services/tier.service';
// import { TokenStorageService } from '../../../services/token-storage.service';
// import { Tier, TierRequest } from '../../../core/models/tier.model';

// CRUD list for loyalty tiers (e.g. Bronze / Silver / Gold).
// Each row shows: name, minPoints, multiplier — all inline editable.
// Tiers are ordered by minPoints ascending (backend returns them sorted).
// multiplier: displayed as e.g. "×1.5" — backend stores as BigDecimal, DTO maps to number.
// Important: deleting a tier does NOT cascade to customers — check if any customers
// hold this tier before deleting, or rely on the backend 409 response.
@Component({
  selector: 'app-tier-list',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './tier-list.component.html',
  styleUrl: './tier-list.component.scss'
})
export class TierListComponent implements OnInit {

  showForm = false;
  editingId: number | null = null;

  ngOnInit(): void {
    // Load tiers via TierService.getByMerchant(merchantId)
  }
}
