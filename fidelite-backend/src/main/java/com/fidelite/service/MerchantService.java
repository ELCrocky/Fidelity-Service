package com.fidelite.service;

import com.fidelite.component.MerchantComponent;
import com.fidelite.component.SettlementLineComponent;
import com.fidelite.component.SettlementPoolComponent;
import com.fidelite.dto.requests.MerchantRequestDTO;
import com.fidelite.dto.responseDTO.MerchantResponseDTO;
import com.fidelite.dto.responseDTO.SettlementLineResponseDTO;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.exceptions.rest.NotFoundRestException;
import com.fidelite.exceptions.technical.NotFoundElementException;
import com.fidelite.mappers.MerchantMapper;
import com.fidelite.mappers.SettlementLineMapper;
import com.fidelite.models.Merchant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// Service layer for merchant management and settlement history queries.
@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantComponent merchantComponent;
    private final SettlementPoolComponent settlementPoolComponent;
    private final SettlementLineComponent settlementLineComponent;
    private final MerchantMapper merchantMapper;
    private final SettlementLineMapper settlementLineMapper;

    // Returns all merchants mapped to response DTOs.
    public List<MerchantResponseDTO> getAll() {
        return merchantComponent.findAll()
                .stream()
                .map(merchantMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Returns a merchant by UUID, translating not-found to a REST exception.
    public MerchantResponseDTO getById(UUID id) {
        try {
            return merchantMapper.toResponse(merchantComponent.findById(id));
        } catch (NotFoundElementException e) {
            throw new NotFoundRestException(e.getMessage());
        }
    }

    // Returns all merchants belonging to the given settlement pool.
    public List<MerchantResponseDTO> getByPool(UUID poolId) {
        return merchantComponent.findByPoolId(poolId)
                .stream()
                .map(merchantMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Creates a merchant, resolves its pool, and auto-generates an API key.
    public MerchantResponseDTO create(MerchantRequestDTO request) {
        try {
            Merchant merchant = merchantMapper.toEntity(request);
            // Resolve the linked pool.
            merchant.setPool(settlementPoolComponent.findById(request.getPoolId()));
            // Generate API key (not accepted from the request).
            merchant.setApiKey(UUID.randomUUID().toString());
            return merchantMapper.toResponse(merchantComponent.save(merchant));
        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public void delete(UUID id) {
        merchantComponent.deleteById(id);
    }

    // Pool situation panel: returns the paginated period-breakdown for this merchant.
    public Page<SettlementLineResponseDTO> getSettlementHistory(UUID merchantId, Pageable pageable) {
        return settlementLineComponent.findByMerchantId(merchantId, pageable)
                .map(settlementLineMapper::toResponse);
    }
}
