package com.fidelite.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Transaction;
import com.fidelite.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionComponent {

    private final TransactionRepository transactionRepository;

}
