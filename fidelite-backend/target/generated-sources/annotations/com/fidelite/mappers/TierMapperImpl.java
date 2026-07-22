package com.fidelite.mappers;

import com.fidelite.dto.requests.TierRequestDTO;
import com.fidelite.dto.responseDTO.TierResponseDTO;
import com.fidelite.models.Tier;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-22T11:53:40+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
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
        tierResponseDTO.setMinPoints( tier.getMinPoints() );
        tierResponseDTO.setMultiplier( tier.getMultiplier() );
        tierResponseDTO.setName( tier.getName() );

        return tierResponseDTO;
    }

    @Override
    public Tier toEntity(TierRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Tier.TierBuilder tier = Tier.builder();

        tier.minPoints( dto.getMinPoints() );
        tier.multiplier( dto.getMultiplier() );
        tier.name( dto.getName() );

        return tier.build();
    }
}
