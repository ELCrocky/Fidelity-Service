export interface Product {
  id: number;
  name: string;
  productType: 'REPAS' | 'DESSERT' | 'BOISSON';
  productPoint: number;
  promotion: boolean;
}

// Used for POST /api/products and PUT /api/products/{id}
export interface ProductRequest {
  name: string;
  productType: 'REPAS' | 'DESSERT' | 'BOISSON';
  productPoint: number;
  promotion: boolean;
  merchantId: string; // UUID from TokenStorageService — backend filters by this
}
