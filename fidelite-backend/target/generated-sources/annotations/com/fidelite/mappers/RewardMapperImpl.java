package com.fidelite.mappers;

import com.fidelite.dto.requests.RewardRequestDTO;
import com.fidelite.dto.responseDTO.RewardResponseDTO;
import com.fidelite.models.Reward;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-22T11:53:39+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class RewardMapperImpl implements RewardMapper {

    @Override
    public RewardResponseDTO toResponse(Reward reward) {
        if ( reward == null ) {
            return null;
        }

        RewardResponseDTO rewardResponseDTO = new RewardResponseDTO();

        rewardResponseDTO.setId( reward.getRewardID() );
        rewardResponseDTO.setCostPoints( reward.getCostPoints() );
        rewardResponseDTO.setName( reward.getName() );

        return rewardResponseDTO;
    }

    @Override
    public Reward toEntity(RewardRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Reward.RewardBuilder reward = Reward.builder();

        reward.costPoints( dto.getCostPoints() );
        reward.name( dto.getName() );

        return reward.build();
    }
}
