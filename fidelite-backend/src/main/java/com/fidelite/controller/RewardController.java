package com.fidelite.controller;

import com.fidelite.dto.requests.RewardRequestDTO;
import com.fidelite.dto.responseDTO.RewardResponseDTO;
import com.fidelite.endpoints.RewardEndpoint;
import com.fidelite.service.RewardService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// REST controller for reward catalogue management and affordability queries.
@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController implements RewardEndpoint {
    private final RewardService rewardService;

    // Returns all rewards offered by the given merchant.
    @Override
    @GetMapping
    public List<RewardResponseDTO> byMerchant(@RequestParam UUID merchantId) {
        return rewardService.getByMerchant(merchantId);
    }

    // Screen 5: returns rewards affordable for the customer given their point balance.
    @Override
    @GetMapping("/affordable")
    public List<RewardResponseDTO> affordable(@RequestParam UUID merchantId,
                                              @RequestParam int balance) {
        return rewardService.getAffordable(merchantId, balance);
    }

    // Creates a new reward for the given merchant.
    @Override
    @PostMapping
    public RewardResponseDTO create(@RequestBody @Valid RewardRequestDTO request) {
        return rewardService.create(request);
    }

    // Deletes the reward with the given UUID.
    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        rewardService.delete(id);
    }
}
