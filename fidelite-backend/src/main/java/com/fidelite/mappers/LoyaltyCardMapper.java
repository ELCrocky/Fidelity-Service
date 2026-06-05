package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.LoyaltyCardRequestDTO;
import com.fidelite.dto.responseDTO.LoyaltyCardResponseDTO;
import com.fidelite.models.LoyaltyCard;

@Mapper(componentModel = "spring")
public interface LoyaltyCardMapper {

    @Mapping(source = "loyaltyCardId", target = "id")
    LoyaltyCardResponseDTO toResponse(LoyaltyCard loyaltyCard);

    @Mapping(target = "loyaltyCardId", ignore = true)
    @Mapping(target = "customer", ignore = true)      // Customer is resolved in service
    @Mapping(target = "pointsBalance", ignore = true) // Starts at 0
    @Mapping(target = "status", ignore = true)        // Defaults to ACTIVE
    @Mapping(target = "transactiona", ignore = true)
    @Mapping(target = "redemptions", ignore = true)
    LoyaltyCard toEntity(LoyaltyCardRequestDTO dto);
}
