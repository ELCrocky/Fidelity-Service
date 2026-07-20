package com.fidelite.mappers;

import com.fidelite.dto.requests.TierRequestDTO;
import com.fidelite.dto.responseDTO.TierResponseDTO;
import com.fidelite.models.Tier;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-20T16:25:35+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class TierMapperImpl implements TierMapper {

    @Override
    public TierResponseDTO toResponse(Tier tier) {
        if ( tier == null ) {
            return null;
        }

        TierResponseDTO tierResponseDTO = new TierResponseDTO();

        tierResponseDTO.setId( tier.getTierID() );
        tierResponseDTO.setName( tier.getName() );
        tierResponseDTO.setMinPoints( tier.getMinPoints() );
        tierResponseDTO.setMultiplier( tier.getMultiplier() );

        return tierResponseDTO;
    }

    @Override
    public Tier toEntity(TierRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Tier.TierBuilder tier = Tier.builder();

        tier.name( dto.getName() );
        tier.minPoints( dto.getMinPoints() );
        tier.multiplier( dto.getMultiplier() );

        return tier.build();
    }
}
