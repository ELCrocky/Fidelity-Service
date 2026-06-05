package com.fidelite.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fidelite.component.MerchantComponent;
import com.fidelite.dto.requests.MerchantRequestDTO;
import com.fidelite.dto.responseDTO.MerchantResponseDTO;
import com.fidelite.mappers.MerchantMapper;
import com.fidelite.models.Merchant;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantComponent merchantComponent;
    private final MerchantMapper merchantMapper;

}
