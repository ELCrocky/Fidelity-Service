package com.fidelite.controller;

import com.fidelite.endpoints.ProductEndpoint;
import com.fidelite.dto.requests.ProductRequestDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import com.fidelite.service.ProductService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

// REST controller for product catalogue management per merchant.
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController implements ProductEndpoint {

     private final ProductService productService;

    // Returns all products for the given merchant.
    @Override
    @GetMapping
    public List<ProductResponseDTO> byMerchant(@RequestParam UUID merchantId) {
        return productService.getByMerchant(merchantId);
    }

    // Creates a new product linked to a merchant.
    @Override
    @PostMapping
    public ProductResponseDTO create(@RequestBody @Valid ProductRequestDTO request) {
        return productService.createProduct(request);
    }

    // Updates an existing product.
    @Override
    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO request) {
        return productService.updateProduct(id, request);
    }

    // Deletes the product with the given ID.
    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
