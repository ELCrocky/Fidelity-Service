package com.fidelite.component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.fidelite.enums.TransactionType;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Transaction;
import com.fidelite.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

// Data-access component for Transaction entities.
@Component
@RequiredArgsConstructor
public class TransactionComponent {

    private final TransactionRepository transactionRepository;

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Returns the paginated transaction history for the given card, newest first.
    public Page<Transaction> findByCardId(UUID cardId, Pageable pageable) {
        return transactionRepository.findByCardLoyaltyCardIdOrderByCreatedAtDesc(cardId, pageable);
    }

    // Looks up a transaction by its idempotency key for duplicate-request detection.
    public Optional<Transaction> findByIdempotencyKey(String key) {
        return transactionRepository.findByIdempotencyKey(key);
    }

    // Returns true if a transaction with the given idempotency key already exists.
    public boolean existsByIdempotencyKey(String key) {
        return transactionRepository.existsByIdempotencyKey(key);
    }

    // Sums points for the given merchant, transaction type, and time window (used in settlement).
    public long sumPointsByMerchantAndTypeAndPeriod(UUID merchantId, TransactionType type,
                                                     LocalDateTime start, LocalDateTime end) {
        return transactionRepository.sumPointsByMerchantAndTypeAndPeriod(merchantId, type, start, end);
    }
}
