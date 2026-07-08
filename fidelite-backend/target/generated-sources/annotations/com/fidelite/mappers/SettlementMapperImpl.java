package com.fidelite.mappers;

import com.fidelite.dto.requests.SettlementRequestDTO;
import com.fidelite.dto.responseDTO.SettlementLineResponseDTO;
import com.fidelite.dto.responseDTO.SettlementResponseDTO;
import com.fidelite.models.Settlement;
import com.fidelite.models.SettlementLine;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-08T16:08:31+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260624-0231, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class SettlementMapperImpl implements SettlementMapper {

    @Autowired
    private SettlementLineMapper settlementLineMapper;

    @Override
    public SettlementResponseDTO toResponse(Settlement settlement) {
        if ( settlement == null ) {
            return null;
        }

        SettlementResponseDTO settlementResponseDTO = new SettlementResponseDTO();

        settlementResponseDTO.setId( settlement.getSettlementId() );
        settlementResponseDTO.setComission( settlement.getCommision() );
        settlementResponseDTO.setLines( settlementLineListToSettlementLineResponseDTOList( settlement.getLines() ) );
        settlementResponseDTO.setCreatedAt( settlement.getCreatedAt() );
        settlementResponseDTO.setNetAmount( settlement.getNetAmount() );
        settlementResponseDTO.setPeriodEnd( settlement.getPeriodEnd() );
        settlementResponseDTO.setPeriodStart( settlement.getPeriodStart() );
        settlementResponseDTO.setPointsIssued( settlement.getPointsIssued() );
        settlementResponseDTO.setPointsRedeemed( settlement.getPointsRedeemed() );

        return settlementResponseDTO;
    }

    @Override
    public Settlement toEntity(SettlementRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Settlement.SettlementBuilder settlement = Settlement.builder();

        settlement.periodEnd( dto.getPeriodEnd() );
        settlement.periodStart( dto.getPeriodStart() );

        return settlement.build();
    }

    protected List<SettlementLineResponseDTO> settlementLineListToSettlementLineResponseDTOList(List<SettlementLine> list) {
        if ( list == null ) {
            return null;
        }

        List<SettlementLineResponseDTO> list1 = new ArrayList<SettlementLineResponseDTO>( list.size() );
        for ( SettlementLine settlementLine : list ) {
            list1.add( settlementLineMapper.toResponse( settlementLine ) );
        }

        return list1;
    }
}
