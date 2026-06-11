package com.fidelite.component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.EarningRule;
import com.fidelite.repository.EarningRuleRepository;

import lombok.RequiredArgsConstructor;

// Data-access component for EarningRule entities.
@Component
@RequiredArgsConstructor
public class EarningRuleComponent {

    private final EarningRuleRepository earningRuleRepository;

    public EarningRule save(EarningRule earningRule) {
        return earningRuleRepository.save(earningRule);
    }

    // Finds an earning rule by ID, throwing a typed exception if absent.
    public EarningRule findById(Long id) throws NotFoundElementException {
        return earningRuleRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("earning rule with id [%s] not found", id)));
    }

    // Returns all earning rules for the given merchant.
    public List<EarningRule> findByMerchantId(UUID merchantId) {
        return earningRuleRepository.findByMerchantIdMerchant(merchantId);
    }

    // Rules valid (active) on the given date.
    public List<EarningRule> findActiveRules(UUID merchantId, LocalDate date) {
        return earningRuleRepository.findActiveRules(merchantId, date);
    }

    public void deleteById(Long id) {
        earningRuleRepository.deleteById(id);
    }
}
