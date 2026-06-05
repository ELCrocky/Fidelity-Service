package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.SettlementRequestDTO;
import com.fidelite.dto.responseDTO.SettlementResponseDTO;
import com.fidelite.models.Settlement;

@Mapper(componentModel = "spring", uses = SettlementLineMapper.class)
public interface SettlementMapper {

    @Mapping(source = "settlementId", target = "id")
    @Mapping(source = "commision", target = "comission")
    @Mapping(source = "lines", target = "lines")
    SettlementResponseDTO toResponse(Settlement settlement);

    @Mapping(target = "settlementId", ignore = true)
    @Mapping(target = "pool", ignore = true)      // Pool is resolved in service
    @Mapping(target = "commision", ignore = true) // Calculated by service
    @Mapping(target = "netAmount", ignore = true) // Calculated by service
    @Mapping(target = "pointsIssued", ignore = true)
    @Mapping(target = "pointsRedeemed", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lines", ignore = true)
    Settlement toEntity(SettlementRequestDTO dto);
}
