package com.fidelite.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fidelite.dto.requests.EarningRuleRequestDTO;
import com.fidelite.dto.responseDTO.EarningRuleResponseDTO;
import com.fidelite.service.EarningRuleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// REST controller for managing point-earning rules per merchant.
@RestController
@RequestMapping("/earning-rules")
@RequiredArgsConstructor
public class EarningRuleController {

    private final EarningRuleService earningRuleService;

    // Returns all earning rules for the given merchant.
    @GetMapping
    public List<EarningRuleResponseDTO> byMerchant(@RequestParam UUID merchantId) {
        return earningRuleService.getByMerchant(merchantId);
    }

    // Returns only currently active earning rules for the given merchant.
    @GetMapping("/active")
    public List<EarningRuleResponseDTO> activeByMerchant(@RequestParam UUID merchantId) {
        return earningRuleService.getActiveByMerchant(merchantId);
    }

    // Creates a new earning rule.
    @PostMapping
    public EarningRuleResponseDTO create(@RequestBody @Valid EarningRuleRequestDTO request) {
        return earningRuleService.create(request);
    }

    // Deletes the earning rule with the given ID.
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        earningRuleService.delete(id);
    }
}
