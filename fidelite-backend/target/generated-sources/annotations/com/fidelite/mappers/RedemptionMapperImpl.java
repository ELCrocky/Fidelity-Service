package com.fidelite.mappers;

import com.fidelite.dto.requests.RedemptionRequestDTO;
import com.fidelite.dto.responseDTO.RedemptionResponseDTO;
import com.fidelite.models.Redemption;
import com.fidelite.models.Reward;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-20T16:25:35+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class RedemptionMapperImpl implements RedemptionMapper {

    @Override
    public RedemptionResponseDTO toResponse(Redemption redemption) {
        if ( redemption == null ) {
            return null;
        }

        RedemptionResponseDTO redemptionResponseDTO = new RedemptionResponseDTO();

        redemptionResponseDTO.setId( redemption.getRedepmtionId() );
        redemptionResponseDTO.setRedeemedAt( redemption.getReddemedAt() );
        redemptionResponseDTO.setRewardName( redemptionRewardName( redemption ) );

        return redemptionResponseDTO;
    }

    @Override
    public Redemption toEntity(RedemptionRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Redemption.RedemptionBuilder redemption = Redemption.builder();

        return redemption.build();
    }

    private String redemptionRewardName(Redemption redemption) {
        Reward reward = redemption.getReward();
        if ( reward == null ) {
            return null;
        }
        return reward.getName();
    }
}
