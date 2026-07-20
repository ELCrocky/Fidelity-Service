package com.fidelite.service;

import com.fidelite.component.MerchantComponent;
import com.fidelite.component.ProductComponent;
import com.fidelite.dto.requests.ProductRequestDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.exceptions.technical.NotFoundElementException;
import com.fidelite.mappers.ProductMapper;
import com.fidelite.models.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Service layer for product catalogue management.
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductComponent productComponent;
    private final MerchantComponent merchantComponent;
    private final ProductMapper productMapper;

    // Returns all products for the given merchant.
    public List<ProductResponseDTO> getByMerchant(java.util.UUID merchantId) {
        return productComponent.findByMerchantId(merchantId)
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Creates a product, resolving its merchant relationship from the request.
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        try {
            Product product = productMapper.toEntity(request);
            // Resolve the relationship from the other component (same pattern as entrepotComponent).
            product.setMerchant(merchantComponent.findById(request.getMerchantId()));
            return productMapper.toResponse(productComponent.save(product));
        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
        try {
            Product product = productComponent.findById(id);
            product.setName(request.getName());
            product.setProductType(request.getProductType());
            product.setProductPoint(request.getProductPoint());
            product.setPromotion(request.isPromotion());
            return productMapper.toResponse(productComponent.save(product));
        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public void deleteProduct(Long id) {
        productComponent.deleteById(id);
    }

}
