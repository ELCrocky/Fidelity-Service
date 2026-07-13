export interface Redemption{
    id: number;
    redeemedAt: string;
    rewardId: string;
    cardId: string;
}

export interface RedemptionRequest {
    cardId: string;
    rewardId: string;
    idempotencyKey: string;
}