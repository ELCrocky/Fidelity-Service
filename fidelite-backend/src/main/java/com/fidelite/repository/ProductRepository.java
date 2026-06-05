package com.fidelite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

import com.fidelite.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Get all products offered by a specific merchant
    List<Product> findByMerchantIdMerchant(UUID merchantId);

    // Find products by merchant and name (used to check for duplicates)
    List<Product> findByMerchantIdMerchantAndName(UUID merchantId, String name);
}
