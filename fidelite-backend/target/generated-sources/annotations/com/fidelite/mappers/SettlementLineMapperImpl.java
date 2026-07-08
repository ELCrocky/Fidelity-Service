package com.fidelite.mappers;

import com.fidelite.dto.requests.SettlementLineRequestDTO;
import com.fidelite.dto.responseDTO.SettlementLineResponseDTO;
import com.fidelite.models.Merchant;
import com.fidelite.models.SettlementLine;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-08T16:08:31+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class SettlementLineMapperImpl implements SettlementLineMapper {

    @Override
    public SettlementLineResponseDTO toResponse(SettlementLine settlementLine) {
        if ( settlementLine == null ) {
            return null;
        }

        SettlementLineResponseDTO settlementLineResponseDTO = new SettlementLineResponseDTO();

        settlementLineResponseDTO.setMerchantId( settlementLineMerchantIdMerchant( settlementLine ) );
        settlementLineResponseDTO.setMerchantName( settlementLineMerchantName( settlementLine ) );
        settlementLineResponseDTO.setId( settlementLine.getId() );
        settlementLineResponseDTO.setNetAmount( settlementLine.getNetAmount() );
        settlementLineResponseDTO.setNetPoints( settlementLine.getNetPoints() );
        settlementLineResponseDTO.setPointsIssued( settlementLine.getPointsIssued() );
        settlementLineResponseDTO.setPointsRedeemed( settlementLine.getPointsRedeemed() );

        return settlementLineResponseDTO;
    }

    @Override
    public SettlementLine toEntity(SettlementLineRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SettlementLine.SettlementLineBuilder settlementLine = SettlementLine.builder();

        return settlementLine.build();
    }

    private UUID settlementLineMerchantIdMerchant(SettlementLine settlementLine) {
        Merchant merchant = settlementLine.getMerchant();
        if ( merchant == null ) {
            return null;
        }
        return merchant.getIdMerchant();
    }

    private String settlementLineMerchantName(SettlementLine settlementLine) {
        Merchant merchant = settlementLine.getMerchant();
        if ( merchant == null ) {
            return null;
        }
        return merchant.getName();
    }
}
