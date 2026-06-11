package com.fidelite.component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Product;
import com.fidelite.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Data-access component for Product entities.
@Component
@RequiredArgsConstructor
public class ProductComponent {

    private final ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    // Finds a product by ID, throwing a typed exception if absent.
    public Product findById(Long id) throws NotFoundElementException {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("product with id [%s] not found", id)));
    }

    // Returns all products belonging to the given merchant.
    public List<Product> findByMerchantId(UUID merchantId) {
        return productRepository.findByMerchantIdMerchant(merchantId);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
