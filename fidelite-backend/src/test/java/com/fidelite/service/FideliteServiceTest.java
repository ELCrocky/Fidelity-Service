package com.fidelite.service;

import com.fidelite.component.FideliteComponent;
import com.fidelite.dto.requests.FideliteRequestDTO;
import com.fidelite.dto.responseDTO.FideliteResponseDTO;
import com.fidelite.mappers.FideliteMapper;
import com.fidelite.models.Fidelite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FideliteServiceTest {

    @Mock
    private FideliteComponent fideliteComponent;

    @Mock
    private FideliteMapper fideliteMapper;

    @InjectMocks
    private FideliteService fideliteService;

    @Test
    void createFidelite_shouldReturnResponseWithGeneratedIdAndBarcode() {
        FideliteRequestDTO request = FideliteRequestDTO.builder()
                .nom("Yılmaz").prenom("Ahmet")
                .mailClient("ahmet@gmail.com").password("123")
                .build();

        Fidelite fidelite = new Fidelite();
        FideliteResponseDTO response = FideliteResponseDTO.builder()
                .idFidelite("some-uuid").barcodeFidelite("1234567890128").pointsActuels(0).build();

        when(fideliteMapper.toEntity(request)).thenReturn(fidelite);
        when(fideliteComponent.existsByBarcodeFidelite(any())).thenReturn(false);
        when(fideliteComponent.save(any())).thenReturn(fidelite);
        when(fideliteMapper.toResponse(fidelite)).thenReturn(response);

        FideliteResponseDTO result = fideliteService.createFidelite(request);

        assertThat(result).isNotNull();
        assertThat(result.getPointsActuels()).isEqualTo(0);
        verify(fideliteComponent).save(any());
    }

    @Test
    void getByBarcode_shouldReturnFidelite() {
        Fidelite fidelite = new Fidelite();
        FideliteResponseDTO response = FideliteResponseDTO.builder().barcodeFidelite("1234567890128").build();

        when(fideliteComponent.findByBarcodeFidelite("1234567890128")).thenReturn(fidelite);
        when(fideliteMapper.toResponse(fidelite)).thenReturn(response);

        FideliteResponseDTO result = fideliteService.getByBarcode("1234567890128");

        assertThat(result.getBarcodeFidelite()).isEqualTo("1234567890128");
    }

    @Test
    void updatePoints_shouldAddPointsToExisting() {
        Fidelite fidelite = new Fidelite();
        fidelite.setPointsActuels(100);

        FideliteResponseDTO response = FideliteResponseDTO.builder().pointsActuels(150).build();

        when(fideliteComponent.findById("id-1")).thenReturn(fidelite);
        when(fideliteComponent.save(fidelite)).thenReturn(fidelite);
        when(fideliteMapper.toResponse(fidelite)).thenReturn(response);

        FideliteResponseDTO result = fideliteService.updatePoints("id-1", 50);

        assertThat(result.getPointsActuels()).isEqualTo(150);
        verify(fideliteComponent).save(fidelite);
    }
}
