package com.fidelite.mappers;

import com.fidelite.dto.requests.RewardRequestDTO;
import com.fidelite.dto.responseDTO.RewardResponseDTO;
import com.fidelite.models.Reward;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-20T16:25:35+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
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
