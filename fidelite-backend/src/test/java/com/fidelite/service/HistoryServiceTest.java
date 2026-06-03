package com.fidelite.service;

import com.fidelite.component.FideliteComponent;
import com.fidelite.component.HistoryComponent;
import com.fidelite.dto.requests.HistoryRequestDTO;
import com.fidelite.dto.responseDTO.HistoryResponseDTO;
import com.fidelite.mappers.HistoryMapper;
import com.fidelite.models.Fidelite;
import com.fidelite.models.History;
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
class HistoryServiceTest {

    @Mock
    private HistoryComponent historyComponent;

    @Mock
    private HistoryMapper historyMapper;

    @Mock
    private FideliteComponent fideliteComponent;

    @InjectMocks
    private HistoryService historyService;

    @Test
    void addTransaction_shouldSaveHistoryWithFideliteAndDate() {
        String idFidelite = "fidelite-1";
        HistoryRequestDTO request = HistoryRequestDTO.builder()
                .points(50).transactionType("GAIN").products(List.of("Kahve")).build();

        Fidelite fidelite = new Fidelite();
        History history = new History();
        HistoryResponseDTO response = HistoryResponseDTO.builder().points(50).build();

        when(historyMapper.toEntity(request)).thenReturn(history);
        when(fideliteComponent.findById(idFidelite)).thenReturn(fidelite);
        when(historyComponent.findByFideliteId(idFidelite)).thenReturn(List.of());
        when(historyComponent.save(any())).thenReturn(history);
        when(historyMapper.toResponse(history)).thenReturn(response);

        HistoryResponseDTO result = historyService.addTransaction(idFidelite, request);

        assertThat(result.getPoints()).isEqualTo(50);
        verify(historyComponent).save(any());
    }

    @Test
    void addTransaction_shouldSetTransactionIdAsNextSequential() {
        String idFidelite = "fidelite-1";
        HistoryRequestDTO request = HistoryRequestDTO.builder().points(30).build();

        History existing1 = new History();
        History existing2 = new History();
        History newHistory = new History();
        HistoryResponseDTO response = HistoryResponseDTO.builder().build();

        when(historyMapper.toEntity(request)).thenReturn(newHistory);
        when(fideliteComponent.findById(idFidelite)).thenReturn(new Fidelite());
        when(historyComponent.findByFideliteId(idFidelite)).thenReturn(List.of(existing1, existing2));
        when(historyComponent.save(any())).thenReturn(newHistory);
        when(historyMapper.toResponse(newHistory)).thenReturn(response);

        historyService.addTransaction(idFidelite, request);

        assertThat(newHistory.getTransactionId()).isEqualTo(3);
    }

    @Test
    void getHistoryByFidelite_shouldReturnMappedList() {
        String idFidelite = "fidelite-1";
        History h1 = new History();
        History h2 = new History();
        HistoryResponseDTO r1 = HistoryResponseDTO.builder().points(10).build();
        HistoryResponseDTO r2 = HistoryResponseDTO.builder().points(20).build();

        when(historyComponent.findByFideliteId(idFidelite)).thenReturn(List.of(h1, h2));
        when(historyMapper.toResponse(h1)).thenReturn(r1);
        when(historyMapper.toResponse(h2)).thenReturn(r2);

        List<HistoryResponseDTO> result = historyService.getHistoryByFidalite(idFidelite);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPoints()).isEqualTo(10);
    }
}
