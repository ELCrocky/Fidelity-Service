package com.fidelite.controller;

import com.fidelite.dto.requests.AppUserRequestDTO;
import com.fidelite.dto.responseDTO.AppUserResponseDTO;
import com.fidelite.endpoints.AppUserEndpoint;
import com.fidelite.service.AppUserService;
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

// REST controller for application user management.
@RestController
@RequestMapping("/api/app-users")
@RequiredArgsConstructor
public class AppUserController implements AppUserEndpoint {

    private final AppUserService appUserService;

    @Override
    @GetMapping
    public List<AppUserResponseDTO> byMerchant(@RequestParam UUID merchantId) {
        return appUserService.getByMerchant(merchantId);
    }

    @Override
    @PostMapping("/register")
    public AppUserResponseDTO register(@RequestBody @Valid AppUserRequestDTO request) {
        return appUserService.register(request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        appUserService.delete(id);
    }
}
