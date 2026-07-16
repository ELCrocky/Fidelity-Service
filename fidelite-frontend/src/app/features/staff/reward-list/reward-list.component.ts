import { Component, inject, OnInit } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Reward } from '../../../core/models/reward.model';
import { RewardService } from '../../../services/reward.service';

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

  // TODO: replace mock data with load() once backend is wired
  protected rewards: Reward[] = [
    { id: 1, name: 'Café Offert', description: 'Un café expresso ou allongé offert', costPoints: 200 },
    { id: 2, name: 'Dessert Gratuit', description: 'Dessert du menu au choix', costPoints: 350 },
    { id: 3, name: 'Repas Offert', description: 'Repas complet pour une personne', costPoints: 800 },
    { id: 4, name: 'Réduction 10%', description: '10% de réduction sur votre prochaine commande', costPoints: 150 },
    { id: 5, name: 'Menu Premium', description: 'Menu 3 plats avec boisson incluse', costPoints: 600 },
    { id: 6, name: 'Livraison Gratuite', description: 'Frais de livraison offerts', costPoints: 100 }
  ];

  protected showForm = false;
  protected editingId: number | null = null;
  protected errorMessage: string | null = null;

  protected readonly rewardForm = this._fb.group({
    name: ['', [Validators.required]],
    description: [''],
    costPoints: [0, [Validators.required, Validators.min(1)]]
  });

  ngOnInit(): void {
    // TODO: call this._rewardService.getByMerchant(merchantId) here once backend is wired
  }

  protected openForm(reward?: Reward): void {
    this.showForm = true;
    this.errorMessage = null;
    if (reward) {
      this.editingId = reward.id;
      this.rewardForm.setValue({
        name: reward.name,
        description: reward.description,
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

  protected deleteReward(id: number): void {
    if (!confirm('Supprimer cette récompense ?')) return;
    this._rewardService.delete(id).subscribe({
      next: () => this.rewards = this.rewards.filter(r => r.id !== id),
      error: () => this.errorMessage = 'Erreur lors de la suppression.'
    });
  }

  protected onSubmit(): void {
    if (this.rewardForm.invalid) return;
    const value = this.rewardForm.getRawValue();
    // TODO: replace '' with TokenStorageService.getMerchantId() once available
    const request = { ...value, merchantId: '' };

    if (this.editingId !== null) {
      this._rewardService.update(this.editingId, request).subscribe({
        next: (updated) => {
          const idx = this.rewards.findIndex(r => r.id === this.editingId);
          if (idx !== -1) this.rewards[idx] = updated;
          this.closeForm();
        },
        error: () => this.errorMessage = 'Une erreur est survenue lors de la modification.'
      });
    } else {
      this._rewardService.create(request).subscribe({
        next: (created) => {
          this.rewards = [...this.rewards, created];
          this.closeForm();
        },
        error: () => this.errorMessage = 'Une erreur est survenue lors de la création.'
      });
    }
  }
}
