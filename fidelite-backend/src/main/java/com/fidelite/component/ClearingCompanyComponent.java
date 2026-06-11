package com.fidelite.component;

import com.fidelite.exceptions.technical.NotFoundElementException;
import com.fidelite.models.ClearingCompany;
import com.fidelite.repository.ClearingCompanyRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Data-access component for ClearingCompany entities.
@Component
@RequiredArgsConstructor
public class ClearingCompanyComponent {

    private final ClearingCompanyRepository clearingCompanyRepository;

    public ClearingCompany save(ClearingCompany clearingCompany) {
        return clearingCompanyRepository.save(clearingCompany);
    }

    // Finds a clearing company by UUID, throwing a typed exception if absent.
    public ClearingCompany findById(UUID id) throws NotFoundElementException {
        return clearingCompanyRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("clearing company with id [%s] not found", id)));
    }

    // Returns all clearing companies.
    public List<ClearingCompany> findAll() {
        return clearingCompanyRepository.findAll();
    }

    public void deleteById(UUID id) {
        clearingCompanyRepository.deleteById(id);
    }
}
