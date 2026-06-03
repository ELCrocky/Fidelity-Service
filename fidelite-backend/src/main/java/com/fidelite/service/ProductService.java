package com.fidelite.service;

import org.springframework.stereotype.Service;

import com.fidelite.component.ProductComponent;
import com.fidelite.dto.requests.ProductRequestDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import com.fidelite.mappers.ProductMapper;
import com.fidelite.models.Product;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProductService{
    private final ProductComponent productComponent;
    private final ProductMapper productMapper;

    public ProductResponseDTO createProduct(ProductRequestDTO request){
        Product product = productMapper.toEntity(request);
        product.setPromotion(false);
        return productMapper.toResponse(productComponent.save(product));
    }

    public List<ProductResponseDTO> getAllProducts(){
        return productComponent.findAll()
        .stream()
        .map(productMapper::toResponse)
        .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> getPromotions(){
        return productComponent.findByPromotionTrue()
        .stream()
        .map(productMapper::toResponse)
        .collect(Collectors.toList());
    }

    public ProductResponseDTO setPromotion(String name, boolean promotion){
        Product product = productComponent.findById(name);
        product.setPromotion(promotion);
        return productMapper.toResponse(productComponent.save(product));
    }

    public void deleteProduct(String name){
        productComponent.deleteById(name);
    }
}