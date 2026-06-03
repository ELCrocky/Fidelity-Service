package com.fidelite.component;

import com.fidelite.enums.Ttype;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.History;
import com.fidelite.repository.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoryComponentTest {

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryComponent historyComponent;

    @Test
    void save_shouldReturnSavedHistory() {
        History history = new History();
        when(historyRepository.save(history)).thenReturn(history);

        assertThat(historyComponent.save(history)).isEqualTo(history);
    }

    @Test
    void findById_shouldReturnHistoryWhenExists() {
        History history = new History();
        when(historyRepository.findById(1)).thenReturn(Optional.of(history));

        assertThat(historyComponent.findById(1)).isEqualTo(history);
    }

    @Test
    void findById_shouldThrowWhenNotFound() {
        when(historyRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> historyComponent.findById(99))
                .isInstanceOf(NotFoundElementException.class);
    }

    @Test
    void findAll_shouldReturnAllHistories() {
        when(historyRepository.findAll()).thenReturn(List.of(new History(), new History()));

        assertThat(historyComponent.findAll()).hasSize(2);
    }

    @Test
    void deleteById_shouldCallRepository() {
        historyComponent.deleteById(1);
        verify(historyRepository).deleteById(1);
    }

    @Test
    void findByFideliteId_shouldReturnHistoriesForFidelite() {
        History h1 = new History();
        when(historyRepository.findByFideliteIdFidelite("fidelite-1")).thenReturn(List.of(h1));

        List<History> result = historyComponent.findByFideliteId("fidelite-1");

        assertThat(result).hasSize(1);
    }

    @Test
    void findByTransactionType_shouldReturnMatchingHistories() {
        when(historyRepository.findByTransactionType(Ttype.GAIN)).thenReturn(List.of(new History()));

        assertThat(historyComponent.findByTransactionType(Ttype.GAIN)).hasSize(1);
    }

    @Test
    void findByTransactionDateBetween_shouldReturnHistoriesInRange() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        when(historyRepository.findByTransactionDateBetween(start, end)).thenReturn(List.of(new History()));

        assertThat(historyComponent.findByTransactionDateBetween(start, end)).hasSize(1);
    }
}
