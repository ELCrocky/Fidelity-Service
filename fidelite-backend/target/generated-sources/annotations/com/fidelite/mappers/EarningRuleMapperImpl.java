package com.fidelite.mappers;

import com.fidelite.dto.requests.EarningRuleRequestDTO;
import com.fidelite.dto.responseDTO.EarningRuleResponseDTO;
import com.fidelite.models.EarningRule;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T16:11:45+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
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
        earningRuleResponseDTO.setValidFrom( earningRule.getValidForm() );
        earningRuleResponseDTO.setCondition( earningRule.getCondition() );
        earningRuleResponseDTO.setPointsAwarded( earningRule.getPointsAwarded() );
        earningRuleResponseDTO.setValidTo( earningRule.getValidTo() );

        return earningRuleResponseDTO;
    }

    @Override
    public EarningRule toEntity(EarningRuleRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        EarningRule.EarningRuleBuilder earningRule = EarningRule.builder();

        earningRule.validForm( dto.getValidFrom() );
        earningRule.condition( dto.getCondition() );
        earningRule.pointsAwarded( dto.getPointsAwarded() );
        earningRule.validTo( dto.getValidTo() );

        return earningRule.build();
    }
}
