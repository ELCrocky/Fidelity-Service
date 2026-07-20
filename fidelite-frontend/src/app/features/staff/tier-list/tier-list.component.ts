import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TierService } from '../../../services/tier.service';
import { TokenStorageService } from '../../../services/token-storage.service';
import { Tier } from '../../../core/models/tier.model';

interface StaffTier extends Tier {
  medal: string;
}

@Component({
  selector: 'app-tier-list',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './tier-list.component.html',
  styleUrl: './tier-list.component.scss'
})
export class TierListComponent implements OnInit {
  private readonly _tierService = inject(TierService);
  private readonly _tokenStorage = inject(TokenStorageService);
  private readonly _cdr = inject(ChangeDetectorRef);

  protected showForm = false;
  protected editingId: number | null = null;
  protected tiers: StaffTier[] = [];

  ngOnInit(): void {
    const merchantId = this._tokenStorage.getMerchantId();
    if (!merchantId) return;
    this._tierService.getByMerchant(merchantId).subscribe({
      next: (tiers) => {
        const sorted = [...tiers].sort((a, b) => a.minPoints - b.minPoints);
        this.tiers = sorted.map((t, i) => ({
          ...t,
          multiplier: Number(t.multiplier),
          medal: String(sorted.length - i)
        }));
        this._cdr.detectChanges();
      }
    });
  }

  protected startEdit(id: number): void {
    this.editingId = id;
    this.showForm = true;
  }

  protected cancel(): void {
    this.showForm = false;
    this.editingId = null;
  }
}
