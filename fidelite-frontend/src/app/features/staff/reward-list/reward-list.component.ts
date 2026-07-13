import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import { RewardService } from '../../../services/reward.service';
// import { TokenStorageService } from '../../../services/token-storage.service';
// import { Reward } from '../../../core/models/reward.model';

// CRUD list for loyalty rewards.
// Each card shows: reward name, costPoints.
// "Ajouter" opens inline form; edit/delete icons on each card.
// RewardService.getByMerchant(merchantId) — check the model for exact field names.
// costPoints: number of points the customer must spend to redeem this reward.
@Component({
  selector: 'app-reward-list',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './reward-list.component.html',
  styleUrl: './reward-list.component.scss'
})
export class RewardListComponent implements OnInit {

  showForm = false;
  editingId: string | null = null;

  ngOnInit(): void {
    // Load rewards via RewardService.getByMerchant(merchantId)
  }
}
