import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// Loyalty tier list with inline edit state for threshold and multiplier.
interface StaffTier {
  id: number;
  name: string;
  minPoints: number;
  multiplier: number;
  medal: string;
}
@Component({
  selector: 'app-tier-list',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './tier-list.component.html',
  styleUrl: './tier-list.component.scss'
})
export class TierListComponent {
  protected showForm = false;
  protected editingId: number | null = null;

  // TODO: replace with TierService.getByMerchant(merchantId), sorted by minPoints asc
  protected readonly tiers: StaffTier[] = [
    { id: 1, name: 'Bronze', minPoints: 0, multiplier: 1, medal: '3' },
    { id: 2, name: 'Silver', minPoints: 300, multiplier: 1.5, medal: '2' },
    { id: 3, name: 'Gold', minPoints: 800, multiplier: 2, medal: '1' }
  ];

  // Puts one tier into inline edit mode.
  protected startEdit(id: number): void {
    this.editingId = id;
    this.showForm = true;
  }

  // Cancels the inline edit state.
  protected cancel(): void {
    this.showForm = false;
    this.editingId = null;
  }
}
