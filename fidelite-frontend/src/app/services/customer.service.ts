import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Customer, CustomerRequest } from '../core/models/customer.model';
import { LoyaltyCard } from '../core/models/loyalty-card.model';

export interface CustomerPage {
  content: Customer[];
  totalElements: number;
}

@Injectable({ providedIn: 'root' })
export class CustomerService {

  private readonly _apiUrl = `${environment.apiUrl}/api/customers`;
  private readonly _cardsUrl = `${environment.apiUrl}/api/cards`;

  constructor(private http: HttpClient) {}

  getByMerchant(merchantId: string, page = 0, size = 100): Observable<CustomerPage> {
    return this.http.get<CustomerPage>(this._apiUrl, {
      params: { merchantId, page: String(page), size: String(size) }
    });
  }

  getCardsByCustomer(customerId: string): Observable<LoyaltyCard[]> {
    return this.http.get<LoyaltyCard[]>(`${this._cardsUrl}/customer/${customerId}`);
  }

  create(merchantId: string, request: CustomerRequest): Observable<Customer> {
    return this.http.post<Customer>(this._apiUrl, request, { params: { merchantId } });
  }
}
