package com.fidelite.mappers;

import com.fidelite.dto.requests.RewardRequestDTO;
import com.fidelite.dto.responseDTO.RewardResponseDTO;
import com.fidelite.models.Reward;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T16:11:39+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
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
        rewardResponseDTO.setName( reward.getName() );
        rewardResponseDTO.setCostPoints( reward.getCostPoints() );

        return rewardResponseDTO;
    }

    @Override
    public Reward toEntity(RewardRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Reward.RewardBuilder reward = Reward.builder();

        reward.name( dto.getName() );
        reward.costPoints( dto.getCostPoints() );

        return reward.build();
    }
}
