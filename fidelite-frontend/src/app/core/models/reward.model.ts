export interface Reward {
  id: number;
  name: string;
  description: string;
  costPoints: number;
}

export interface RewardRequest {
  name: string;
  description: string;
  costPoints: number;
  merchantId: string;
}
