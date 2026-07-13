export interface Settlement{
    id: number;
    periodStart: string;
    periodEnd: string;
    pointsIssued: number;
    pointsRedeemed: number;
    netAmount: number;
    comission: number;
    createdAt: string;
    poolId: string;
}