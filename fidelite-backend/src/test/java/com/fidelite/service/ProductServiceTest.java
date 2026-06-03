package com.fidelite.service;

import com.fidelite.component.ProductComponent;
import com.fidelite.dto.requests.ProductRequestDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import com.fidelite.enums.Ptype;
import com.fidelite.mappers.ProductMapper;
import com.fidelite.models.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductComponent productComponent;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_shouldSetPromotionFalseAndSave() {
        ProductRequestDTO request = ProductRequestDTO.builder()
                .name("Kahve").productType(Ptype.BOISSON).productPoint(10).build();

        Product product = new Product();
        ProductResponseDTO response = ProductResponseDTO.builder().name("Kahve").build();

        when(productMapper.toEntity(request)).thenReturn(product);
        when(productComponent.save(any())).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(response);

        ProductResponseDTO result = productService.createProduct(request);

        assertThat(product.isPromotion()).isFalse();
        assertThat(result.getName()).isEqualTo("Kahve");
        verify(productComponent).save(product);
    }

    @Test
    void getAllProducts_shouldReturnMappedList() {
        Product p1 = new Product();
        Product p2 = new Product();
        ProductResponseDTO r1 = ProductResponseDTO.builder().name("Kahve").build();
        ProductResponseDTO r2 = ProductResponseDTO.builder().name("Çay").build();

        when(productComponent.findAll()).thenReturn(List.of(p1, p2));
        when(productMapper.toResponse(p1)).thenReturn(r1);
        when(productMapper.toResponse(p2)).thenReturn(r2);

        List<ProductResponseDTO> result = productService.getAllProducts();

        assertThat(result).hasSize(2);
    }

    @Test
    void getPromotions_shouldReturnOnlyPromotedProducts() {
        Product p1 = new Product();
        ProductResponseDTO r1 = ProductResponseDTO.builder().name("Tart").build();

        when(productComponent.findByPromotionTrue()).thenReturn(List.of(p1));
        when(productMapper.toResponse(p1)).thenReturn(r1);

        List<ProductResponseDTO> result = productService.getPromotions();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Tart");
    }

    @Test
    void setPromotion_shouldUpdatePromotionAndSave() {
        Product product = new Product();
        product.setPromotion(false);
        ProductResponseDTO response = ProductResponseDTO.builder().name("Kahve").build();

        when(productComponent.findById("Kahve")).thenReturn(product);
        when(productComponent.save(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(response);

        productService.setPromotion("Kahve", true);

        assertThat(product.isPromotion()).isTrue();
        verify(productComponent).save(product);
    }

    @Test
    void deleteProduct_shouldCallDeleteById() {
        productService.deleteProduct("Kahve");
        verify(productComponent).deleteById("Kahve");
    }
}
