package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.MerchantRequestDTO;
import com.fidelite.dto.responseDTO.MerchantResponseDTO;
import com.fidelite.models.Merchant;

@Mapper(componentModel = "spring")
public interface MerchantMapper {

    @Mapping(source = "idMerchant", target = "id")
    @Mapping(source = "pool.spId", target = "poolId")
    MerchantResponseDTO toResponse(Merchant merchant);

    @Mapping(target = "idMerchant", ignore = true)
    @Mapping(target = "pool", ignore = true)      // Pool is resolved in service
    @Mapping(target = "apiKey", ignore = true)    // API key is generated in service
    @Mapping(target = "status", ignore = true)    // Defaults to ACTIVE
    @Mapping(target = "createdAt", ignore = true) // Set automatically
    Merchant toEntity(MerchantRequestDTO dto);
}
