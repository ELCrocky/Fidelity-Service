package com.fidelite.component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.SettlementPool;
import com.fidelite.repository.SettlementPoolRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Data-access component for SettlementPool entities.
@Component
@RequiredArgsConstructor
public class SettlementPoolComponent {

    private final SettlementPoolRepository settlementPoolRepository;

    public SettlementPool save(SettlementPool pool) {
        return settlementPoolRepository.save(pool);
    }

    // Finds a settlement pool by UUID, throwing a typed exception if absent.
    public SettlementPool findById(UUID id) throws NotFoundElementException {
        return settlementPoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("settlement pool with id [%s] not found", id)));
    }

    // Returns all settlement pools belonging to the given clearing company.
    public List<SettlementPool> findByClearingCompanyId(UUID clearingCompanyId) {
        return settlementPoolRepository.findByClearingCompanyCcId(clearingCompanyId);
    }

    public void deleteById(UUID id) {
        settlementPoolRepository.deleteById(id);
    }
}
