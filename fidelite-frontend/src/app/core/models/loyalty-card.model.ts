export type CardStatus = 'ACTIVE' | 'BLOCKED' | 'LOST';

export interface LoyaltyCard{
    id: string;
    barcodeEan13: string;
    pointsBalance: number;
    status: CardStatus;
    customerId: string;
}