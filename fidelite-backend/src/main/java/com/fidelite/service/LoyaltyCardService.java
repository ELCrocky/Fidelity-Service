package com.fidelite.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fidelite.component.LoyaltyCardComponent;
import com.fidelite.dto.requests.LoyaltyCardRequestDTO;
import com.fidelite.dto.responseDTO.LoyaltyCardResponseDTO;
import com.fidelite.mappers.LoyaltyCardMapper;
import com.fidelite.models.LoyaltyCard;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoyaltyCardService {

    private final LoyaltyCardComponent loyaltyCardComponent;
    private final LoyaltyCardMapper loyaltyCardMapper;

}
