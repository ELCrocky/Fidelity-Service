export interface LoginRequest{
    email: string;
    password: string;
}

export interface LoginResponse {
    token: string;
    userType: 'APP_USER' | 'CLEARING_USER';
    role: 'MERCHANT_ADMIN' | 'STAFF' | 'CLEARING';
}