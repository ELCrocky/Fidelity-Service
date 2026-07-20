import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';
import { CustomerService } from '../../../services/customer.service';
import { ProductService } from '../../../services/product.service';
import { TierService } from '../../../services/tier.service';
import { RewardService } from '../../../services/reward.service';
import { TransactionService } from '../../../services/transaction.service';
import { Transaction } from '../../../core/models/transaction.model';
import { Reward } from '../../../core/models/reward.model';
import { TokenStorageService } from '../../../services/token-storage.service';

interface DonutSlice {
  label: string;
  color: string;
  pct: number;
  dasharray: string;
  dashoffset: string;
}

interface SalesBar {
  name: string;
  count: number;
  pct: number;
  color: string;
}

@Component({
  selector: 'app-staff-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './staff-home.component.html',
  styleUrl: './staff-home.component.scss'
})
export class StaffHomeComponent implements OnInit {
  private readonly _customerService = inject(CustomerService);
  private readonly _productService = inject(ProductService);
  private readonly _tierService = inject(TierService);
  private readonly _rewardService = inject(RewardService);
  private readonly _transactionService = inject(TransactionService);
  private readonly _tokenStorage = inject(TokenStorageService);
  private readonly _cdr = inject(ChangeDetectorRef);

  protected totalCustomers = 0;
  protected totalProducts = 0;
  protected totalTiers = 0;
  protected totalRewards = 0;

  protected salesBars: SalesBar[] = [];
  protected tierSlices: DonutSlice[] = [];
  protected tierLegend: { name: string; color: string; count: number; pct: number }[] = [];
  protected rewards: Reward[] = [];
  protected recentTransactions: Transaction[] = [];

  private readonly C = 2 * Math.PI * 54;

  ngOnInit(): void {
    const merchantId = this._tokenStorage.getMerchantId();
    if (!merchantId) return;

    forkJoin({
      customers: this._customerService.getByMerchant(merchantId),
      products: this._productService.getByMerchant(merchantId),
      tiers: this._tierService.getByMerchant(merchantId),
      rewards: this._rewardService.getByMerchant(merchantId),
      sales: this._transactionService.getProductSales(merchantId),
      recent: this._transactionService.getByMerchant(merchantId, 0, 10),
    }).subscribe({
      next: ({ customers, products, tiers, rewards, sales, recent }) => {
        this.totalCustomers = customers.totalElements;
        this.totalProducts = products.length;
        this.totalTiers = tiers.length;
        this.totalRewards = rewards.length;

        const barColors = ['#f97316', '#8b5cf6', '#3b82f6', '#22c55e', '#ec4899', '#f59e0b', '#14b8a6', '#ef4444'];
        const top = sales.slice(0, 8);
        const max = top[0]?.salesCount ?? 1;
        this.salesBars = top.map((s, i) => ({
          name: s.productName,
          count: s.salesCount,
          pct: Math.round((s.salesCount / max) * 100),
          color: barColors[i % barColors.length]
        }));

        const tierPalette = ['#f59e0b', '#94a3b8', '#cd853f', '#22c55e', '#ec4899'];
        const sorted = [...tiers].sort((a, b) => a.minPoints - b.minPoints);

        // Group customers by tierName
        const tierCount: Record<string, number> = {};
        for (const c of customers.content) {
          const name = c.tierName ?? 'Sans palier';
          tierCount[name] = (tierCount[name] ?? 0) + 1;
        }
        const total = customers.totalElements || 1;
        const donutItems = sorted.map((t, i) => ({
          label: t.name,
          count: tierCount[t.name] ?? 0,
          color: tierPalette[i % tierPalette.length]
        })).filter(x => x.count > 0);

        this.tierSlices = this.buildDonut(donutItems);
        this.tierLegend = donutItems.map(x => ({
          name: x.label,
          color: x.color,
          count: x.count,
          pct: Math.round((x.count / total) * 100)
        }));

        this.rewards = rewards;
        this.recentTransactions = recent.content;
        this._cdr.detectChanges();
      }
    });
  }

  protected formatDate(dt: string): string {
    if (!dt) return '—';
    return new Date(dt).toLocaleDateString('fr-FR', { day: '2-digit', month: 'short', year: 'numeric' });
  }

  private buildDonut(items: { label: string; count: number; color: string }[]): DonutSlice[] {
    const total = items.reduce((s, i) => s + i.count, 0);
    if (total === 0) return [];
    let cumulative = 0;
    return items.map(item => {
      const pct = item.count / total;
      const arc = pct * this.C;
      const offset = -(cumulative / total) * this.C;
      cumulative += item.count;
      return {
        label: item.label,
        color: item.color,
        pct: Math.round(pct * 100),
        dasharray: `${arc.toFixed(2)} ${(this.C - arc).toFixed(2)}`,
        dashoffset: `${offset.toFixed(2)}`
      };
    });
  }
}
