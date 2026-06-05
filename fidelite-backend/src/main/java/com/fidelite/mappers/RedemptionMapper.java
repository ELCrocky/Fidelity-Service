package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.RedemptionRequestDTO;
import com.fidelite.dto.responseDTO.RedemptionResponseDTO;
import com.fidelite.models.Redemption;

@Mapper(componentModel = "spring")
public interface RedemptionMapper {

    @Mapping(source = "redepmtionId", target = "id")
    @Mapping(source = "reddemedAt", target = "redeemedAt")
    @Mapping(source = "reward.name", target = "rewardName")
    RedemptionResponseDTO toResponse(Redemption redemption);

    @Mapping(target = "redepmtionId", ignore = true)
    @Mapping(target = "card", ignore = true)       // Card is resolved in service
    @Mapping(target = "reward", ignore = true)     // Reward is resolved in service
    @Mapping(target = "reddemedAt", ignore = true)
    Redemption toEntity(RedemptionRequestDTO dto);
}
