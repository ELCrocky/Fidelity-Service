package com.fidelite.mappers;

import com.fidelite.dto.requests.EarningRuleRequestDTO;
import com.fidelite.dto.responseDTO.EarningRuleResponseDTO;
import com.fidelite.models.EarningRule;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-20T16:25:35+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class EarningRuleMapperImpl implements EarningRuleMapper {

    @Override
    public EarningRuleResponseDTO toResponse(EarningRule earningRule) {
        if ( earningRule == null ) {
            return null;
        }

        EarningRuleResponseDTO earningRuleResponseDTO = new EarningRuleResponseDTO();

        earningRuleResponseDTO.setId( earningRule.getEarningRuleId() );
        earningRuleResponseDTO.setCondition( earningRule.getCondition() );
        earningRuleResponseDTO.setPointsAwarded( earningRule.getPointsAwarded() );
        earningRuleResponseDTO.setValidFrom( earningRule.getValidFrom() );
        earningRuleResponseDTO.setValidTo( earningRule.getValidTo() );

        return earningRuleResponseDTO;
    }

    @Override
    public EarningRule toEntity(EarningRuleRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        EarningRule.EarningRuleBuilder earningRule = EarningRule.builder();

        earningRule.condition( dto.getCondition() );
        earningRule.pointsAwarded( dto.getPointsAwarded() );
        earningRule.validFrom( dto.getValidFrom() );
        earningRule.validTo( dto.getValidTo() );

        return earningRule.build();
    }
}
