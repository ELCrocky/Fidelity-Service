package com.fidelite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fidelite.component.TransactionComponent;
import com.fidelite.dto.requests.TransactionRequestDTO;
import com.fidelite.dto.responseDTO.TransactionResponseDTO;
import com.fidelite.mappers.TransactionMapper;
import com.fidelite.models.Transaction;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionComponent transactionComponent;
    private final TransactionMapper transactionMapper;

}
