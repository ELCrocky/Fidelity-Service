package com.fidelite.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fidelite.component.EarningRuleComponent;
import com.fidelite.component.MerchantComponent;
import com.fidelite.dto.requests.EarningRuleRequestDTO;
import com.fidelite.dto.responseDTO.EarningRuleResponseDTO;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.mappers.EarningRuleMapper;
import com.fidelite.models.EarningRule;

import lombok.RequiredArgsConstructor;

// Service layer for earning rule management.
@Service
@RequiredArgsConstructor
public class EarningRuleService {

private final EarningRuleComponent earningRuleComponent;
    private final MerchantComponent merchantComponent;
    private final EarningRuleMapper earningRuleMapper;

    // Returns all earning rules for the given merchant.
    public List<EarningRuleResponseDTO> getByMerchant(UUID merchantId) {
        return earningRuleComponent.findByMerchantId(merchantId)
                .stream()
                .map(earningRuleMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Returns only earning rules that are active today for the given merchant.
    public List<EarningRuleResponseDTO> getActiveByMerchant(UUID merchantId) {
        return earningRuleComponent.findActiveRules(merchantId, LocalDate.now())
                .stream()
                .map(earningRuleMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Creates a new earning rule, resolving the merchant entity from the request.
    public EarningRuleResponseDTO create(EarningRuleRequestDTO request) {
        try {
            EarningRule rule = earningRuleMapper.toEntity(request);
            rule.setMerchant(merchantComponent.findById(request.getMerchantId()));
            return earningRuleMapper.toResponse(earningRuleComponent.save(rule));
        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public void delete(Long id) {
        earningRuleComponent.deleteById(id);
    }

}
