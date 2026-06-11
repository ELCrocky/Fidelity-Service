package com.fidelite.service;

import com.fidelite.component.ClearingCompanyComponent;
import com.fidelite.component.SettlementPoolComponent;
import com.fidelite.dto.requests.SettlementPoolRequestDTO;
import com.fidelite.dto.responseDTO.SettlementPoolResponseDTO;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.exceptions.rest.NotFoundRestException;
import com.fidelite.exceptions.technical.NotFoundElementException;
import com.fidelite.mappers.SettlementPoolMapper;
import com.fidelite.models.SettlementPool;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Service layer for settlement pool management.
@Service
@RequiredArgsConstructor
public class SettlementPoolService {
    private final SettlementPoolComponent settlementPoolComponent;
    private final ClearingCompanyComponent clearingCompanyComponent;
    private final SettlementPoolMapper settlementPoolMapper;

    // Returns a settlement pool by UUID, translating not-found to a REST exception.
    public SettlementPoolResponseDTO getById(UUID id) {
        try {
            return settlementPoolMapper.toResponse(settlementPoolComponent.findById(id));
        } catch (NotFoundElementException e) {
            throw new NotFoundRestException(e.getMessage());
        }
    }

    // Returns all settlement pools belonging to the given clearing company.
    public List<SettlementPoolResponseDTO> getByClearingCompany(UUID clearingCompanyId) {
        return settlementPoolComponent.findByClearingCompanyId(clearingCompanyId)
                .stream()
                .map(settlementPoolMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Creates a new settlement pool and links it to its clearing company.
    public SettlementPoolResponseDTO create(SettlementPoolRequestDTO request) {
        try {
            SettlementPool pool = settlementPoolMapper.toEntity(request);
            pool.setClearingCompany(clearingCompanyComponent.findById(request.getClearingCompanyId()));
            return settlementPoolMapper.toResponse(settlementPoolComponent.save(pool));
        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public void delete(UUID id) {
        settlementPoolComponent.deleteById(id);
    }
}
