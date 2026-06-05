package com.fidelite.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Redemption;
import com.fidelite.repository.RedemptionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedemptionComponent {

    private final RedemptionRepository redemptionRepository;

}
