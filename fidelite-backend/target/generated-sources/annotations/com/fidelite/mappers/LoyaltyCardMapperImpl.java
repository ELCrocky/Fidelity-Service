package com.fidelite.mappers;

import com.fidelite.dto.requests.LoyaltyCardRequestDTO;
import com.fidelite.dto.responseDTO.LoyaltyCardResponseDTO;
import com.fidelite.models.LoyaltyCard;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-22T11:53:40+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class LoyaltyCardMapperImpl implements LoyaltyCardMapper {

    @Override
    public LoyaltyCardResponseDTO toResponse(LoyaltyCard loyaltyCard) {
        if ( loyaltyCard == null ) {
            return null;
        }

        LoyaltyCardResponseDTO loyaltyCardResponseDTO = new LoyaltyCardResponseDTO();

        loyaltyCardResponseDTO.setId( loyaltyCard.getLoyaltyCardId() );
        loyaltyCardResponseDTO.setBarcodeEan13( loyaltyCard.getBarcodeEan13() );
        loyaltyCardResponseDTO.setPointsBalance( loyaltyCard.getPointsBalance() );
        loyaltyCardResponseDTO.setStatus( loyaltyCard.getStatus() );

        return loyaltyCardResponseDTO;
    }

    @Override
    public LoyaltyCard toEntity(LoyaltyCardRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        LoyaltyCard.LoyaltyCardBuilder loyaltyCard = LoyaltyCard.builder();

        loyaltyCard.barcodeEan13( dto.getBarcodeEan13() );

        return loyaltyCard.build();
    }
}
