package com.fidelite.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fidelite.component.RewardComponent;
import com.fidelite.dto.requests.RewardRequestDTO;
import com.fidelite.dto.responseDTO.RewardResponseDTO;
import com.fidelite.mappers.RewardMapper;
import com.fidelite.models.Reward;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final RewardComponent rewardComponent;
    private final RewardMapper rewardMapper;

}
