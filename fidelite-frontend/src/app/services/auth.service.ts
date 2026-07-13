import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginRequest, LoginResponse } from '../core/models/auth.model';
import { TokenStorageService } from './token-storage.service';

@Injectable({providedIn: 'root'})
export class AuthService {

    private readonly apiUrl = `${environment.apiUrl}/api/auth`;

    constructor(
        private http: HttpClient,
        private tokenStorage: TokenStorageService
    ) {}

    loginBorne(request: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(`${this.apiUrl}/login`, request)
            .pipe(tap(res =>this.storeSession(res)));
    }

    loginClearing(request: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(`${this.apiUrl}/clearing/login`, request)
            .pipe(tap(res => this.storeSession(res)));
    }

    logout(): void {
    this.tokenStorage.clear();
  }

  isLoggedIn(): boolean {
    return this.tokenStorage.getToken() !== null;
  }

  hasRole(...roles: string[]): boolean {
    const current = this.tokenStorage.getRole();
    return current !== null && roles.includes(current);
  }

  private storeSession(res: LoginResponse): void {
    this.tokenStorage.saveToken(res.token);
    this.tokenStorage.saveRole(res.role);
    this.tokenStorage.saveUserType(res.userType);
  }
}