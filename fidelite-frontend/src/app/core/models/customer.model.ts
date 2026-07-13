export interface Customer{
    id: string;
    nom: string;
    prenom: string;
    email: string;
    phone: string;
    dateNaissance: string;
    marketingConsent: boolean;
}

export interface CustomerRequest{
    nom: string;
    prenom: string;
    email: string;
    phone?: string;
    dateNaissance: string;
    marketingConsent: boolean;
    merchantId: string;
}