package com.fidelite.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fidelite.component.AppUserComponent;
import com.fidelite.dto.requests.AppUserRequestDTO;
import com.fidelite.dto.responseDTO.AppUserResponseDTO;
import com.fidelite.mappers.AppUserMapper;
import com.fidelite.models.AppUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserComponent appUserComponent;
    private final AppUserMapper appUserMapper;

}
