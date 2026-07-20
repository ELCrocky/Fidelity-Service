import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Transaction } from '../core/models/transaction.model';

export interface TransactionPage {
  content: Transaction[];
  totalElements: number;
}

@Injectable({ providedIn: 'root' })
export class TransactionService {

  private readonly _apiUrl = `${environment.apiUrl}/api/transactions`;

  constructor(private http: HttpClient) {}

  getByMerchant(merchantId: string, page = 0, size = 100): Observable<TransactionPage> {
    return this.http.get<TransactionPage>(this._apiUrl, {
      params: { merchantId, page: String(page), size: String(size) }
    });
  }

  getByCard(cardId: string, page = 0, size = 20): Observable<TransactionPage> {
    return this.http.get<TransactionPage>(`${this._apiUrl}/card/${cardId}`, {
      params: { page: String(page), size: String(size) }
    });
  }

  getProductSales(merchantId: string): Observable<{ productName: string; salesCount: number }[]> {
    return this.http.get<{ productName: string; salesCount: number }[]>(`${this._apiUrl}/product-sales`, {
      params: { merchantId }
    });
  }
}
