package com.fidelite.controller;

import com.fidelite.dto.requests.ClearingCompanyRequestDTO;
import com.fidelite.dto.responseDTO.ClearingCompanyResponseDTO;
import com.fidelite.endpoints.ClearingCompanyEndpoint;
import com.fidelite.service.ClearingCompanyService;
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
import org.springframework.web.bind.annotation.RestController;

// REST controller implementing clearing company CRUD endpoints.
@RestController
@RequestMapping("/api/clearing-companies")
@RequiredArgsConstructor
public class ClearingCompanyController implements ClearingCompanyEndpoint {

    private final ClearingCompanyService clearingCompanyService;

    // Returns all clearing companies.
    @Override
    @GetMapping
    public List<ClearingCompanyResponseDTO> all() {
        return clearingCompanyService.getAll();
    }

    // Returns a clearing company by its UUID.
    @Override
    @GetMapping("/{id}")
    public ClearingCompanyResponseDTO byId(@PathVariable UUID id) {
        return clearingCompanyService.getById(id);
    }

    // Creates a new clearing company.
    @Override
    @PostMapping
    public ClearingCompanyResponseDTO create(@RequestBody @Valid ClearingCompanyRequestDTO request) {
        return clearingCompanyService.create(request);
    }

    // Deletes the clearing company with the given UUID.
    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        clearingCompanyService.delete(id);
    }
}
