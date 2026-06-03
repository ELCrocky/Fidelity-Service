package com.fidelite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fidelite.dto.requests.HistoryRequestDTO;
import com.fidelite.dto.responseDTO.HistoryResponseDTO;
import com.fidelite.service.HistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @PostMapping("/{idFidelite}")
    public ResponseEntity<HistoryResponseDTO> addTransaction(
            @PathVariable String idFidelite,
            @RequestBody HistoryRequestDTO request) {
        return ResponseEntity.ok(historyService.addTransaction(idFidelite, request));
    }

    @GetMapping("/{idFidelite}")
    public ResponseEntity<List<HistoryResponseDTO>> getHistoryByFidelite(@PathVariable String idFidelite) {
        return ResponseEntity.ok(historyService.getHistoryByFidalite(idFidelite));
    }
}
