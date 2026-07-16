import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Product, ProductRequest } from '../core/models/product.model';

@Injectable({ providedIn: 'root' })
export class ProductService {

  private readonly apiUrl = `${environment.apiUrl}/api/products`;

  constructor(private http: HttpClient) {}

  // Returns all products belonging to the merchant (backend filters by JWT merchantId)
  getByMerchant(merchantId: string): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl, { params: { merchantId } });
  }

  create(request: ProductRequest): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, request);
  }

  update(id: number, request: ProductRequest): Observable<Product> {
    return this.http.put<Product>(`${this.apiUrl}/${id}`, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
