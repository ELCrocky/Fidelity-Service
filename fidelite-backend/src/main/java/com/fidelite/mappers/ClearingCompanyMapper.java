package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.ClearingCompanyRequestDTO;
import com.fidelite.dto.responseDTO.ClearingCompanyResponseDTO;
import com.fidelite.models.ClearingCompany;

@Mapper(componentModel = "spring")
public interface ClearingCompanyMapper {

    @Mapping(source = "ccId", target = "id")
    ClearingCompanyResponseDTO toResponse(ClearingCompany clearingCompany);

    @Mapping(target = "ccId", ignore = true)
    @Mapping(target = "totalRevenue", ignore = true) // Starts at zero, updated by settlements
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "pools", ignore = true)
    ClearingCompany toEntity(ClearingCompanyRequestDTO dto);
}
