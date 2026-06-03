package com.fidelite.component;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fidelite.enums.Ttype;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.History;
import com.fidelite.repository.HistoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HistoryComponent {

    private final HistoryRepository historyRepository;

    public History save(History history) {
        return historyRepository.save(history);
    }

    public History findById(Integer id) throws NotFoundElementException {
        return historyRepository.findById(id)
            .orElseThrow(() -> new NotFoundElementException(String.format("history with id [%s] not found", id)));
    }

    public List<History> findAll() {
        return historyRepository.findAll();
    }

    public void deleteById(Integer id) {
        historyRepository.deleteById(id);
    }

    public List<History> findByFideliteId(String idFidelite) {
        return historyRepository.findByFideliteIdFidelite(idFidelite);
    }

    public List<History> findByTransactionType(Ttype type) {
        return historyRepository.findByTransactionType(type);
    }

    public List<History> findByTransactionDateBetween(LocalDateTime start, LocalDateTime end) {
        return historyRepository.findByTransactionDateBetween(start, end);
    }
}