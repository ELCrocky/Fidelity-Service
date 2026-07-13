
import {Injectable} from '@angular/core';

const TOKEN_KEY = 'auth-token';
const ROLE_KEY = 'auth-role';
const USER_TYPE_KEY = 'auth-user-type';

@Injectable({ providedIn: 'root'})
export class TokenStorageService {

    saveToken(token: string): void {
        sessionStorage.setItem(TOKEN_KEY, token);
    }

    getToken(): string | null {
        return sessionStorage.getItem(TOKEN_KEY);
    }

    saveRole(role:string): void {
        sessionStorage.setItem(ROLE_KEY, role);
    }

    getRole(): string | null {
        return sessionStorage.getItem(ROLE_KEY);
    }

    saveUserType(userType: string): void{
        sessionStorage.setItem(USER_TYPE_KEY, userType);
    }

    getUserType(): string | null {
        return sessionStorage.getItem(USER_TYPE_KEY);
    }

    clear(): void{
        sessionStorage.clear();
    }
}