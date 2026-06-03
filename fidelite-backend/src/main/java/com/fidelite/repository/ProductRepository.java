package com.fidelite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.fidelite.enums.Ptype;
import com.fidelite.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    
    List<Product> findByProductType(Ptype type);
    
    List<Product> findByPromotionTrue();
}
