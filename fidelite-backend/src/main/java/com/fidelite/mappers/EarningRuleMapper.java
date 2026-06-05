package com.fidelite.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fidelite.dto.requests.EarningRuleRequestDTO;
import com.fidelite.dto.responseDTO.EarningRuleResponseDTO;
import com.fidelite.models.EarningRule;

@Mapper(componentModel = "spring")
public interface EarningRuleMapper {

    @Mapping(source = "earningRuleId", target = "id")
    @Mapping(source = "validForm", target = "validFrom") // entity field is misspelled as validForm
    EarningRuleResponseDTO toResponse(EarningRule earningRule);

    @Mapping(target = "earningRuleId", ignore = true)
    @Mapping(target = "merchant", ignore = true)         // Merchant is resolved in service
    @Mapping(source = "validFrom", target = "validForm") // map back to misspelled entity field
    EarningRule toEntity(EarningRuleRequestDTO dto);
}
