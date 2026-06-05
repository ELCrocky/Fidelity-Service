package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.RewardRequestDTO;
import com.fidelite.dto.responseDTO.RewardResponseDTO;
import com.fidelite.models.Reward;

@Mapper(componentModel = "spring")
public interface RewardMapper {

    @Mapping(source = "rewardID", target = "id")
    RewardResponseDTO toResponse(Reward reward);

    @Mapping(target = "rewardID", ignore = true)
    @Mapping(target = "merchant", ignore = true) // Merchant is resolved in service
    Reward toEntity(RewardRequestDTO dto);
}
