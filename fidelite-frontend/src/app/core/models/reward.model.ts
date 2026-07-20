export interface Reward {
  id: string; // UUID
  name: string;
  description?: string;
  costPoints: number;
}

export interface RewardRequest {
  name: string;
  description: string;
  costPoints: number;
  merchantId: string;
}
