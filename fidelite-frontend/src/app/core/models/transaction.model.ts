export type TransactionType = 'EARN' | 'REDEEM' | 'ADJUST' | 'EXPIRE';

export interface Transaction {
    id: number;
    points: number;
    type: TransactionType;
    createdAt: string;
    cardId: string;
}

export interface TransactionRequest {
    cardId: string;
    productIds: number[];
    idempotencyKey: string;
}