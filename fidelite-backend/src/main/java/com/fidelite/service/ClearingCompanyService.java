package com.fidelite.service;

import com.fidelite.component.ClearingCompanyComponent;
import com.fidelite.dto.requests.ClearingCompanyRequestDTO;
import com.fidelite.dto.responseDTO.ClearingCompanyResponseDTO;
import com.fidelite.exceptions.rest.NotFoundRestException;
import com.fidelite.exceptions.technical.NotFoundElementException;
import com.fidelite.mappers.ClearingCompanyMapper;
import com.fidelite.models.ClearingCompany;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Service layer for clearing company CRUD operations.
@Service
@RequiredArgsConstructor
public class ClearingCompanyService {

    private final ClearingCompanyComponent clearingCompanyComponent;
    private final ClearingCompanyMapper clearingCompanyMapper;

    // Returns all clearing companies mapped to response DTOs.
    public List<ClearingCompanyResponseDTO> getAll() {
        return clearingCompanyComponent.findAll()
                .stream()
                .map(clearingCompanyMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Returns a clearing company by UUID, translating technical not-found to a REST exception.
    public ClearingCompanyResponseDTO getById(UUID id) {
        try {
            return clearingCompanyMapper.toResponse(clearingCompanyComponent.findById(id));
        } catch (NotFoundElementException e) {
            throw new NotFoundRestException(e.getMessage());
        }
    }

    // Creates and persists a new clearing company from the request DTO.
    public ClearingCompanyResponseDTO create(ClearingCompanyRequestDTO request) {
        ClearingCompany entity = clearingCompanyMapper.toEntity(request);
        return clearingCompanyMapper.toResponse(clearingCompanyComponent.save(entity));
    }

    public void delete(UUID id) {
        clearingCompanyComponent.deleteById(id);
    }
}
