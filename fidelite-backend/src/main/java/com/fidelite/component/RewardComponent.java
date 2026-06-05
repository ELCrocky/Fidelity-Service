package com.fidelite.component;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Reward;
import com.fidelite.repository.RewardRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RewardComponent {

    private final RewardRepository rewardRepository;

}
