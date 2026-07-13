package com.fidelite.mappers;

import com.fidelite.dto.requests.SettlementPoolRequestDTO;
import com.fidelite.dto.responseDTO.SettlementPoolResponseDTO;
import com.fidelite.models.SettlementPool;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-09T15:34:21+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class SettlementPoolMapperImpl implements SettlementPoolMapper {

    @Override
    public SettlementPoolResponseDTO toResponse(SettlementPool settlementPool) {
        if ( settlementPool == null ) {
            return null;
        }

        SettlementPoolResponseDTO settlementPoolResponseDTO = new SettlementPoolResponseDTO();

        settlementPoolResponseDTO.setId( settlementPool.getSpId() );
        settlementPoolResponseDTO.setBalance( settlementPool.getBalance() );
        settlementPoolResponseDTO.setCommissionPerInvoice( settlementPool.getCommissionPerInvoice() );
        settlementPoolResponseDTO.setName( settlementPool.getName() );
        settlementPoolResponseDTO.setPointValue( settlementPool.getPointValue() );

        return settlementPoolResponseDTO;
    }

    @Override
    public SettlementPool toEntity(SettlementPoolRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SettlementPool.SettlementPoolBuilder settlementPool = SettlementPool.builder();

        settlementPool.commissionPerInvoice( dto.getCommissionPerInvoice() );
        settlementPool.name( dto.getName() );
        settlementPool.pointValue( dto.getPointValue() );

        return settlementPool.build();
    }
}
