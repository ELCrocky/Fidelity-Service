package com.fidelite.mappers;

import com.fidelite.dto.requests.SettlementPoolRequestDTO;
import com.fidelite.dto.responseDTO.SettlementPoolResponseDTO;
import com.fidelite.models.SettlementPool;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-20T16:25:35+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
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
        settlementPoolResponseDTO.setName( settlementPool.getName() );
        settlementPoolResponseDTO.setPointValue( settlementPool.getPointValue() );
        settlementPoolResponseDTO.setBalance( settlementPool.getBalance() );
        settlementPoolResponseDTO.setCommissionPerInvoice( settlementPool.getCommissionPerInvoice() );

        return settlementPoolResponseDTO;
    }

    @Override
    public SettlementPool toEntity(SettlementPoolRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SettlementPool.SettlementPoolBuilder settlementPool = SettlementPool.builder();

        settlementPool.name( dto.getName() );
        settlementPool.pointValue( dto.getPointValue() );
        settlementPool.commissionPerInvoice( dto.getCommissionPerInvoice() );

        return settlementPool.build();
    }
}
