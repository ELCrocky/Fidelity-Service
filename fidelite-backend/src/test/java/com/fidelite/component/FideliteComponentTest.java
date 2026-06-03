package com.fidelite.component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Fidelite;
import com.fidelite.repository.FideliteRepository;
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
class FideliteComponentTest {

    @Mock
    private FideliteRepository fideliteRepository;

    @InjectMocks
    private FideliteComponent fideliteComponent;

    @Test
    void save_shouldReturnSavedFidelite() {
        Fidelite fidelite = new Fidelite();
        when(fideliteRepository.save(fidelite)).thenReturn(fidelite);

        Fidelite result = fideliteComponent.save(fidelite);

        assertThat(result).isEqualTo(fidelite);
    }

    @Test
    void findById_shouldReturnFideliteWhenExists() {
        Fidelite fidelite = new Fidelite();
        when(fideliteRepository.findById("id-1")).thenReturn(Optional.of(fidelite));

        Fidelite result = fideliteComponent.findById("id-1");

        assertThat(result).isEqualTo(fidelite);
    }

    @Test
    void findById_shouldThrowWhenNotFound() {
        when(fideliteRepository.findById("id-x")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fideliteComponent.findById("id-x"))
                .isInstanceOf(NotFoundElementException.class);
    }

    @Test
    void findAll_shouldReturnAllFidelites() {
        when(fideliteRepository.findAll()).thenReturn(List.of(new Fidelite(), new Fidelite()));

        List<Fidelite> result = fideliteComponent.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteById_shouldCallRepository() {
        fideliteComponent.deleteById("id-1");
        verify(fideliteRepository).deleteById("id-1");
    }

    @Test
    void findByMailClient_shouldReturnFideliteWhenExists() {
        Fidelite fidelite = new Fidelite();
        when(fideliteRepository.findByMailClient("test@mail.com")).thenReturn(Optional.of(fidelite));

        Fidelite result = fideliteComponent.findByMailClient("test@mail.com");

        assertThat(result).isEqualTo(fidelite);
    }

    @Test
    void findByMailClient_shouldThrowWhenNotFound() {
        when(fideliteRepository.findByMailClient("x@mail.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fideliteComponent.findByMailClient("x@mail.com"))
                .isInstanceOf(NotFoundElementException.class);
    }

    @Test
    void findByBarcodeFidelite_shouldReturnFideliteWhenExists() {
        Fidelite fidelite = new Fidelite();
        when(fideliteRepository.findByBarcodeFidelite("1234567890128")).thenReturn(Optional.of(fidelite));

        Fidelite result = fideliteComponent.findByBarcodeFidelite("1234567890128");

        assertThat(result).isEqualTo(fidelite);
    }

    @Test
    void existsByBarcodeFidelite_shouldReturnTrueWhenExists() {
        when(fideliteRepository.findByBarcodeFidelite("1234567890128")).thenReturn(Optional.of(new Fidelite()));

        assertThat(fideliteComponent.existsByBarcodeFidelite("1234567890128")).isTrue();
    }

    @Test
    void existsByBarcodeFidelite_shouldReturnFalseWhenNotExists() {
        when(fideliteRepository.findByBarcodeFidelite("0000000000000")).thenReturn(Optional.empty());

        assertThat(fideliteComponent.existsByBarcodeFidelite("0000000000000")).isFalse();
    }
}
