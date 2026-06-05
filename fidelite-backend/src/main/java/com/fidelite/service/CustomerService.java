package com.fidelite.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fidelite.component.CustomerComponent;
import com.fidelite.dto.requests.CustomerRequestDTO;
import com.fidelite.dto.responseDTO.CustomerResponseDTO;
import com.fidelite.mappers.CustomerMapper;
import com.fidelite.models.Customer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerComponent customerComponent;
    private final CustomerMapper customerMapper;

}
