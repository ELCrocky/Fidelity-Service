package com.fidelite.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Tier;
import com.fidelite.repository.TierRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TierComponent {

    private final TierRepository tierRepository;

}
