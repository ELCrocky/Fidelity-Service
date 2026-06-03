package com.fidelite.component;

import com.fidelite.enums.Ptype;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Product;
import com.fidelite.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductComponentTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductComponent productComponent;

    @Test
    void save_shouldReturnSavedProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        assertThat(productComponent.save(product)).isEqualTo(product);
    }

    @Test
    void findById_shouldReturnProductWhenExists() {
        Product product = new Product();
        when(productRepository.findById("Kahve")).thenReturn(Optional.of(product));

        assertThat(productComponent.findById("Kahve")).isEqualTo(product);
    }

    @Test
    void findById_shouldThrowWhenNotFound() {
        when(productRepository.findById("Yok")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productComponent.findById("Yok"))
                .isInstanceOf(NotFoundElementException.class);
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(new Product(), new Product()));

        assertThat(productComponent.findAll()).hasSize(2);
    }

    @Test
    void deleteById_shouldCallRepository() {
        productComponent.deleteById("Kahve");
        verify(productRepository).deleteById("Kahve");
    }

    @Test
    void findByProductType_shouldReturnMatchingProducts() {
        when(productRepository.findByProductType(Ptype.BOISSON)).thenReturn(List.of(new Product()));

        assertThat(productComponent.findByProductType(Ptype.BOISSON)).hasSize(1);
    }

    @Test
    void findByPromotionTrue_shouldReturnPromotedProducts() {
        when(productRepository.findByPromotionTrue()).thenReturn(List.of(new Product()));

        assertThat(productComponent.findByPromotionTrue()).hasSize(1);
    }
}
