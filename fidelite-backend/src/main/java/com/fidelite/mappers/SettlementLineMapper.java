package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.SettlementLineRequestDTO;
import com.fidelite.dto.responseDTO.SettlementLineResponseDTO;
import com.fidelite.models.SettlementLine;

@Mapper(componentModel = "spring")
public interface SettlementLineMapper {

    @Mapping(source = "merchant.idMerchant", target = "merchantId")
    @Mapping(source = "merchant.name", target = "merchantName")
    SettlementLineResponseDTO toResponse(SettlementLine settlementLine);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "settlement", ignore = true)    // Set in service
    @Mapping(target = "merchant", ignore = true)      // Resolved in service
    @Mapping(target = "pointsIssued", ignore = true)  // Calculated by service
    @Mapping(target = "pointsRedeemed", ignore = true)
    @Mapping(target = "netPoints", ignore = true)
    @Mapping(target = "netAmount", ignore = true)
    SettlementLine toEntity(SettlementLineRequestDTO dto);
}
