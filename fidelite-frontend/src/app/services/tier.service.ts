import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Tier, TierRequest } from '../core/models/tier.model';

@Injectable({ providedIn: 'root' })
export class TierService {

  private readonly apiUrl = `${environment.apiUrl}/api/tiers`;

  constructor(private http: HttpClient) {}

  // Returns tiers defined for the merchant, ordered by minPoints asc
  getByMerchant(merchantId: string): Observable<Tier[]> {
    return this.http.get<Tier[]>(this.apiUrl, { params: { merchantId } });
  }

  create(request: TierRequest): Observable<Tier> {
    return this.http.post<Tier>(this.apiUrl, request);
  }

  update(id: number, request: TierRequest): Observable<Tier> {
    return this.http.put<Tier>(`${this.apiUrl}/${id}`, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
