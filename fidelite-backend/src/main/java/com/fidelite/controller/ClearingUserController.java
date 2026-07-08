package com.fidelite.controller;

import com.fidelite.dto.requests.ClearingUserRequestDTO;
import com.fidelite.dto.responseDTO.ClearingUserResponseDTO;
import com.fidelite.endpoints.ClearingUserEndpoint;
import com.fidelite.service.ClearingUserService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST controller for clearing company staff user management.
@RestController
@RequestMapping("/api/clearing-users")
@RequiredArgsConstructor
public class ClearingUserController implements ClearingUserEndpoint {

    private final ClearingUserService clearingUserService;

    @Override
    @PostMapping("/register")
    public ClearingUserResponseDTO register(@RequestBody @Valid ClearingUserRequestDTO request) {
        return clearingUserService.register(request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        clearingUserService.delete(id);
    }
}
