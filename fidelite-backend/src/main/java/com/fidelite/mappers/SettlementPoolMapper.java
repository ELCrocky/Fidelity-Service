package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.SettlementPoolRequestDTO;
import com.fidelite.dto.responseDTO.SettlementPoolResponseDTO;
import com.fidelite.models.SettlementPool;

@Mapper(componentModel = "spring")
public interface SettlementPoolMapper {

    @Mapping(source = "spId", target = "id")
    SettlementPoolResponseDTO toResponse(SettlementPool settlementPool);

    @Mapping(target = "spId", ignore = true)
    @Mapping(target = "clearingCompany", ignore = true) // Resolved in service
    @Mapping(target = "balance", ignore = true)         // Starts at zero
    SettlementPool toEntity(SettlementPoolRequestDTO dto);
}
