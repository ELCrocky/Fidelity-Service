package com.fidelite.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.EarningRule;
import com.fidelite.repository.EarningRuleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EarningRuleComponent {

    private final EarningRuleRepository earningRuleRepository;

}
