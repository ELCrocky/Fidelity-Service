package com.fidelite.service;

import com.fidelite.component.MerchantComponent;
import com.fidelite.component.RewardComponent;
import com.fidelite.dto.requests.RewardRequestDTO;
import com.fidelite.dto.responseDTO.RewardResponseDTO;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.exceptions.technical.NotFoundElementException;
import com.fidelite.mappers.RewardMapper;
import com.fidelite.models.Reward;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Service layer for reward catalogue management.
@Service
@RequiredArgsConstructor
public class RewardService {
    private final RewardComponent rewardComponent;
    private final MerchantComponent merchantComponent;
    private final RewardMapper rewardMapper;

    // Returns all rewards offered by the given merchant.
    public List<RewardResponseDTO> getByMerchant(UUID merchantId) {
        return rewardComponent.findByMerchantId(merchantId)
                .stream()
                .map(rewardMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Screen 5: returns rewards the customer can afford with their current point balance.
    public List<RewardResponseDTO> getAffordable(UUID merchantId, int balance) {
        return rewardComponent.findAffordable(merchantId, balance)
                .stream()
                .map(rewardMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Creates a new reward, resolving its merchant from the request.
    public RewardResponseDTO create(RewardRequestDTO request) {
        try {
            Reward reward = rewardMapper.toEntity(request);
            reward.setMerchant(merchantComponent.findById(request.getMerchantId()));
            return rewardMapper.toResponse(rewardComponent.save(reward));
        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public void delete(UUID id) {
        rewardComponent.deleteById(id);
    }
}
