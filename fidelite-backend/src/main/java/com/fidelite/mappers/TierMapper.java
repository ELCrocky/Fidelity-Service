package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.TierRequestDTO;
import com.fidelite.dto.responseDTO.TierResponseDTO;
import com.fidelite.models.Tier;

@Mapper(componentModel = "spring")
public interface TierMapper {

    @Mapping(source = "tierID", target = "id")
    TierResponseDTO toResponse(Tier tier);

    @Mapping(target = "tierID", ignore = true)
    @Mapping(target = "merchant", ignore = true) // Merchant is resolved in service
    Tier toEntity(TierRequestDTO dto);
}
