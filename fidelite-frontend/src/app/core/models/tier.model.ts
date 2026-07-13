export interface Tier {
  id: number;
  name: string;
  minPoints: number;
  multiplier: number; // e.g. 1.5 means 1.5× points earned
}

// Used for POST /api/tiers and PUT /api/tiers/{id}
export interface TierRequest {
  name: string;
  minPoints: number;
  multiplier: number;
  merchantId: string; // UUID from TokenStorageService
}
