package com.fidelite.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fidelite.component.FideliteComponent;
import com.fidelite.component.HistoryComponent;
import com.fidelite.dto.requests.HistoryRequestDTO;
import com.fidelite.mappers.HistoryMapper;
import com.fidelite.models.History;
import com.fidelite.dto.responseDTO.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class HistoryService {

    private final HistoryComponent historyComponent;
    private final HistoryMapper historyMapper;
    private final FideliteComponent fideliteComponent;

    public HistoryResponseDTO addTransaction(String idFidelite, HistoryRequestDTO request){
        History history = historyMapper.toEntity(request);
        history.setFidelite(fideliteComponent.findById(idFidelite));
        history.setTransactionDate(LocalDateTime.now());
        history.setTransactionId(historyComponent.findByFideliteId(idFidelite).size() +1);
        return historyMapper.toResponse(historyComponent.save(history));
    }

    public List<HistoryResponseDTO> getHistoryByFidalite(String idFidelite){
        return historyComponent.findByFideliteId(idFidelite)
        .stream()
        .map(historyMapper::toResponse)
        .collect(Collectors.toList());
    }
}