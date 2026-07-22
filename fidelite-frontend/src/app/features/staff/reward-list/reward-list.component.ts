import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Reward } from '../../../core/models/reward.model';
import { RewardService } from '../../../services/reward.service';
import { TokenStorageService } from '../../../services/token-storage.service';

// Reward card grid with create/edit/delete backed by RewardService.
@Component({
  selector: 'app-reward-list',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './reward-list.component.html',
  styleUrl: './reward-list.component.scss'
})
export class RewardListComponent implements OnInit {
  private readonly _fb = inject(NonNullableFormBuilder);
  private readonly _rewardService = inject(RewardService);
  private readonly _tokenStorage = inject(TokenStorageService);
  private readonly _cdr = inject(ChangeDetectorRef);

  protected rewards: Reward[] = [];
  protected showForm = false;

  protected currentPage = 0;
  protected readonly pageSize = 15;

  protected get totalPages(): number { return Math.max(1, Math.ceil(this.rewards.length / this.pageSize)); }
  protected get pageStart(): number { return this.currentPage * this.pageSize + 1; }
  protected get pageEnd(): number { return Math.min(this.rewards.length, (this.currentPage + 1) * this.pageSize); }
  protected get pagedRewards(): Reward[] {
    const s = this.currentPage * this.pageSize;
    return this.rewards.slice(s, s + this.pageSize);
  }
  protected get pageNumbers(): number[] { return Array.from({ length: this.totalPages }, (_, i) => i); }
  protected prevPage(): void { if (this.currentPage > 0) this.currentPage--; }
  protected nextPage(): void { if (this.currentPage < this.totalPages - 1) this.currentPage++; }
  protected goToPage(p: number): void { this.currentPage = p; }
  protected editingId: string | null = null;
  protected errorMessage: string | null = null;

  protected readonly rewardForm = this._fb.group({
    name: ['', [Validators.required]],
    description: [''],
    costPoints: [0, [Validators.required, Validators.min(1)]]
  });

  ngOnInit(): void {
    const merchantId = this._tokenStorage.getMerchantId();
    if (!merchantId) return;
    this._rewardService.getByMerchant(merchantId).subscribe({
      next: (rewards) => { this.rewards = rewards; this._cdr.detectChanges(); },
      error: () => this.errorMessage = 'Impossible de charger les récompenses.'
    });
  }

  protected openForm(reward?: Reward): void {
    this.showForm = true;
    this.errorMessage = null;
    if (reward) {
      this.editingId = reward.id;
      this.rewardForm.setValue({
        name: reward.name,
        description: reward.description ?? '',
        costPoints: reward.costPoints
      });
    } else {
      this.editingId = null;
      this.rewardForm.reset({ costPoints: 0 });
    }
  }

  protected closeForm(): void {
    this.showForm = false;
    this.editingId = null;
    this.errorMessage = null;
    this.rewardForm.reset();
  }

  protected deleteReward(id: string): void {
    if (!confirm('Supprimer cette récompense ?')) return;
    this._rewardService.delete(id).subscribe({
      next: () => this.rewards = this.rewards.filter(r => r.id !== id),
      error: () => this.errorMessage = 'Erreur lors de la suppression.'
    });
  }

  protected onSubmit(): void {
    if (this.rewardForm.invalid) return;
    const value = this.rewardForm.getRawValue();
    const request = { ...value, merchantId: this._tokenStorage.getMerchantId() ?? '' };

    this._rewardService.create(request).subscribe({
        next: (created) => {
          this.rewards = [...this.rewards, created];
          this.closeForm();
        },
        error: () => this.errorMessage = 'Une erreur est survenue lors de la création.'
      });
  }
}
