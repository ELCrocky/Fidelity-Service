package com.fidelite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fidelite.component.TierComponent;
import com.fidelite.dto.requests.TierRequestDTO;
import com.fidelite.dto.responseDTO.TierResponseDTO;
import com.fidelite.mappers.TierMapper;
import com.fidelite.models.Tier;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TierService {

    private final TierComponent tierComponent;
    private final TierMapper tierMapper;

}
