package com.fidelite.service;


import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fidelite.component.FideliteComponent;
import com.fidelite.dto.requests.FideliteRequestDTO;
import com.fidelite.dto.responseDTO.FideliteResponseDTO;
import com.fidelite.mappers.FideliteMapper;
import com.fidelite.models.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class FideliteService{
    private final FideliteComponent fideliteComponent;
    private final FideliteMapper fideliteMapper;

    public FideliteResponseDTO createFidelite (FideliteRequestDTO request){
        Fidelite fidelite = fideliteMapper.toEntity(request);
        fidelite.setIdFidelite(UUID.randomUUID().toString());
        fidelite.setBarcodeFidelite(generateBarcode());
        fidelite.setPointsActuels(0);
        return fideliteMapper.toResponse(fideliteComponent.save(fidelite));
    }

    public FideliteResponseDTO getByBarcode(String barcode){
        return fideliteMapper.toResponse(fideliteComponent.findByBarcodeFidelite(barcode));
    }

    public FideliteResponseDTO updatePoints(String fideliteId, int points){
        Fidelite fidelite = fideliteComponent.findById(fideliteId);
        fidelite.setPointsActuels(fidelite.getPointsActuels() + points);
        return fideliteMapper.toResponse(fideliteComponent.save(fidelite));
    }

    private String generateBarcode(){
        String barcode;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 12; i++){
                sb.append((int)(Math.random() * 10));
            }
            int sum = 0;
            for (int i = 0; i < 12; i++){
                int digit = Character.getNumericValue(sb.charAt(i));
                sum += (i % 2 == 0) ? digit : digit * 3;
            }
            sb.append((10 - (sum % 10)) % 10);
            barcode = sb.toString();
        } while (fideliteComponent.existsByBarcodeFidelite(barcode));

        return barcode;
    }

}