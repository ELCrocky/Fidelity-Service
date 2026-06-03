package com.fidelite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fidelite.dto.requests.ProductRequestDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import com.fidelite.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/promotions")
    public ResponseEntity<List<ProductResponseDTO>> getPromotions() {
        return ResponseEntity.ok(productService.getPromotions());
    }

    @PatchMapping("/{name}/promotion")
    public ResponseEntity<ProductResponseDTO> setPromotion(
            @PathVariable String name,
            @RequestParam boolean promotion) {
        return ResponseEntity.ok(productService.setPromotion(name, promotion));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String name) {
        productService.deleteProduct(name);
        return ResponseEntity.noContent().build();
    }
}
