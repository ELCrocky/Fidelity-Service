package com.fidelite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fidelite.dto.requests.FideliteRequestDTO;
import com.fidelite.dto.responseDTO.FideliteResponseDTO;
import com.fidelite.service.FideliteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fidelite")
@RequiredArgsConstructor
public class FideliteController {

    private final FideliteService fideliteService;

    @PostMapping
    public ResponseEntity<FideliteResponseDTO> createFidelite(@RequestBody FideliteRequestDTO request) {
        return ResponseEntity.ok(fideliteService.createFidelite(request));
    }

    @GetMapping("/{barcode}")
    public ResponseEntity<FideliteResponseDTO> getByBarcode(@PathVariable String barcode) {
        return ResponseEntity.ok(fideliteService.getByBarcode(barcode));
    }

    @PatchMapping("/{id}/points")
    public ResponseEntity<FideliteResponseDTO> updatePoints(@PathVariable String id, @RequestParam int points) {
        return ResponseEntity.ok(fideliteService.updatePoints(id, points));
    }
}
