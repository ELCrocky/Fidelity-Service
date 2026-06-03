package com.fidelite.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fidelite.enums.Ptype;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Product;
import com.fidelite.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductComponent {

    private final ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findById(String name) throws NotFoundElementException {
        return productRepository.findById(name)
            .orElseThrow(() -> new NotFoundElementException(String.format("product with name [%s] not found", name)));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteById(String name) {
        productRepository.deleteById(name);
    }

    public List<Product> findByProductType(Ptype type) {
        return productRepository.findByProductType(type);
    }

    public List<Product> findByPromotionTrue() {
        return productRepository.findByPromotionTrue();
    }
}
