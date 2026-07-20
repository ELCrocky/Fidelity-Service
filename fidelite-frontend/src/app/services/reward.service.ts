import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Reward, RewardRequest } from '../core/models/reward.model';

@Injectable({ providedIn: 'root' })
export class RewardService {

  private readonly apiUrl = `${environment.apiUrl}/api/rewards`;

  constructor(private http: HttpClient) {}

  getByMerchant(merchantId: string): Observable<Reward[]> {
    return this.http.get<Reward[]>(this.apiUrl, { params: { merchantId } });
  }

  create(request: RewardRequest): Observable<Reward> {
    return this.http.post<Reward>(this.apiUrl, request);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
