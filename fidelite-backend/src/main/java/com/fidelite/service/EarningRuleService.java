package com.fidelite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fidelite.component.EarningRuleComponent;
import com.fidelite.dto.requests.EarningRuleRequestDTO;
import com.fidelite.dto.responseDTO.EarningRuleResponseDTO;
import com.fidelite.mappers.EarningRuleMapper;
import com.fidelite.models.EarningRule;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EarningRuleService {

    private final EarningRuleComponent earningRuleComponent;
    private final EarningRuleMapper earningRuleMapper;

}
