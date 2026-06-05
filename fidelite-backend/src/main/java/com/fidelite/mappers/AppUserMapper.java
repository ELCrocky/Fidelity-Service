package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.AppUserRequestDTO;
import com.fidelite.dto.responseDTO.AppUserResponseDTO;
import com.fidelite.models.AppUser;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    @Mapping(source = "appUserId", target = "id")
    @Mapping(source = "merchant.idMerchant", target = "merchantId")
    AppUserResponseDTO toResponse(AppUser appUser);

    @Mapping(target = "appUserId", ignore = true)
    @Mapping(target = "passwordHash", ignore = true) // BCrypt hashing is done in service
    @Mapping(target = "merchant", ignore = true)     // Merchant is resolved in service
    AppUser toEntity(AppUserRequestDTO dto);
}
