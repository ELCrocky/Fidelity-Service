package com.fidelite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fidelite.component.RedemptionComponent;
import com.fidelite.dto.requests.RedemptionRequestDTO;
import com.fidelite.dto.responseDTO.RedemptionResponseDTO;
import com.fidelite.mappers.RedemptionMapper;
import com.fidelite.models.Redemption;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedemptionService {

    private final RedemptionComponent redemptionComponent;
    private final RedemptionMapper redemptionMapper;

}
