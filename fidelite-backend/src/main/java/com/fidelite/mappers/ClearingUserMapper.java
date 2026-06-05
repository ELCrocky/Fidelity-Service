package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.ClearingUserRequestDTO;
import com.fidelite.dto.responseDTO.ClearingUserResponseDTO;
import com.fidelite.models.ClearingUser;

@Mapper(componentModel = "spring")
public interface ClearingUserMapper {

    @Mapping(source = "cuId", target = "id")
    @Mapping(source = "passwordHash", target = "email") // entity field named passwordHash holds the email
    @Mapping(source = "clearingCompany.ccId", target = "clearingCompanyId")
    ClearingUserResponseDTO toResponse(ClearingUser clearingUser);

    @Mapping(target = "cuId", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)     // BCrypt hashing is done in service
    @Mapping(target = "clearingCompany", ignore = true)  // Resolved in service
    ClearingUser toEntity(ClearingUserRequestDTO dto);
}
