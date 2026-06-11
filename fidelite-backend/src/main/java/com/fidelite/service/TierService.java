package com.fidelite.service;

import com.fidelite.component.MerchantComponent;
import com.fidelite.component.TierComponent;
import com.fidelite.dto.requests.TierRequestDTO;
import com.fidelite.dto.responseDTO.TierResponseDTO;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.exceptions.technical.NotFoundElementException;
import com.fidelite.mappers.TierMapper;
import com.fidelite.models.Tier;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Service layer for loyalty tier management.
@Service
@RequiredArgsConstructor
public class TierService {
    private final TierComponent tierComponent;
    private final MerchantComponent merchantComponent;
    private final TierMapper tierMapper;

    // Returns all tiers for the given merchant.
    public List<TierResponseDTO> getByMerchant(UUID merchantId) {
        return tierComponent.findByMerchantId(merchantId)
                .stream()
                .map(tierMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Creates a new tier, resolving its merchant from the request.
    public TierResponseDTO create(TierRequestDTO request) {
        try {
            Tier tier = tierMapper.toEntity(request);
            tier.setMerchant(merchantComponent.findById(request.getMerchantId()));
            return tierMapper.toResponse(tierComponent.save(tier));
        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public void delete(Long id) {
        tierComponent.deleteById(id);
    }
}
