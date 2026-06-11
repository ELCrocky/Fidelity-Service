package com.fidelite.service;

import com.fidelite.component.LoyaltyCardComponent;
import com.fidelite.component.ProductComponent;
import com.fidelite.component.TierComponent;
import com.fidelite.component.TransactionComponent;
import com.fidelite.dto.requests.TransactionRequestDTO;
import com.fidelite.dto.responseDTO.TransactionResponseDTO;
import com.fidelite.enums.TransactionType;
import com.fidelite.exceptions.rest.BadRequestRestException;
import com.fidelite.exceptions.rest.NotFoundRestException;
import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.mappers.TransactionMapper;
import com.fidelite.models.LoyaltyCard;
import com.fidelite.models.Product;
import com.fidelite.models.Tier;
import com.fidelite.models.Transaction;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Service layer for point transactions: earn, redeem, adjust, expire.
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionComponent transactionComponent;
    private final LoyaltyCardComponent loyaltyCardComponent;
    private final TierComponent tierComponent;
    private final ProductComponent productComponent;
    private final TransactionMapper transactionMapper;

    /**
     * Point earn/spend transaction. Steps:
     * 1. Idempotency check (option A: if the key was already processed, return the original result).
     * 2. Find the card.
     * 3. Apply the tier multiplier (based on the card holder's tier), rounded to nearest integer.
     * 4. Add the points to the card and persist the transaction (atomic).
     * 5. Automatically update the tier based on the new balance.
     */
    @Transactional
    public TransactionResponseDTO create(TransactionRequestDTO request) {
        try {
            // 1. Idempotency: if the key is present and already processed, return the original result.
            if (request.getIdempotencyKey() != null && !request.getIdempotencyKey().isBlank()) {
                var existing = transactionComponent.findByIdempotencyKey(request.getIdempotencyKey());
                if (existing.isPresent()) {
                    return transactionMapper.toResponse(existing.get());
                }
            }

            // 2. Find the card.
            LoyaltyCard card = loyaltyCardComponent.findById(request.getCardId());

            // 3. Calculate points. For EARN apply tier multiplier; use base points for other types.
            int finalPoints = request.getPoints();
            if (request.getType() == TransactionType.EARN) {
                finalPoints = applyTierMultiplier(card, request.getPoints());
            }

            // 4. Build the transaction.
            Transaction transaction = Transaction.builder()
                    .points(finalPoints)
                    .type(request.getType())
                    .idempotencyKey(request.getIdempotencyKey())
                    .card(card)
                    .products(resolveProducts(request.getProductsId()))
                    .build();

            // Update balance: EARN adds, REDEEM/EXPIRE subtracts.
            int newBalance = applyToBalance(card.getPointsBalance(), request.getType(), finalPoints);
            if (newBalance < 0) {
                throw new BadRequestRestException("insufficient point balance");
            }
            card.setPointsBalance(newBalance);

            // 5. Update tier based on new balance.
            updateTier(card);

            loyaltyCardComponent.save(card);
            Transaction saved = transactionComponent.save(transaction);
            return transactionMapper.toResponse(saved);

        } catch (NotFoundElementException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    // Returns the paginated transaction history for the given loyalty card.
    public Page<TransactionResponseDTO> getByCard(UUID cardId, Pageable pageable) {
        return transactionComponent.findByCardId(cardId, pageable)
                .map(transactionMapper::toResponse);
    }

    // --- helper methods ---

    // Multiplies base points by the card holder's tier multiplier, rounded to nearest integer.
    private int applyTierMultiplier(LoyaltyCard card, int basePoints) {
        Tier tier = card.getCustomer().getTier();
        if (tier == null || tier.getMultiplier() == null) {
            return basePoints; // no tier means multiplier of 1.0
        }
        BigDecimal result = BigDecimal.valueOf(basePoints).multiply(tier.getMultiplier());
        return result.setScale(0, RoundingMode.HALF_UP).intValue(); // round to nearest integer
    }

    // Increases or decreases the balance according to the transaction type.
    private int applyToBalance(int current, TransactionType type, int points) {
        return switch (type) {
            case EARN, ADJUST -> current + points;
            case REDEEM, EXPIRE -> current - points;
        };
    }

    // Finds the highest qualifying tier for the card's new balance and assigns it (auto tier upgrade/downgrade).
    private void updateTier(LoyaltyCard card) {
        UUID merchantId = card.getCustomer().getMerchant().getIdMerchant();
        List<Tier> tiers = tierComponent.findByMerchantIdOrderByMinPointsDesc(merchantId);
        // Start from the highest threshold; the first tier the balance meets is the customer's tier.
        for (Tier tier : tiers) {
            if (card.getPointsBalance() >= tier.getMinPoints()) {
                card.getCustomer().setTier(tier);
                return;
            }
        }
        // If no threshold is met, the customer remains without a tier (or at the lowest level).
    }

    // Resolves a list of product IDs into Product entities.
    private List<Product> resolveProducts(List<Long> productIds) throws NotFoundElementException {
        List<Product> products = new ArrayList<>();
        if (productIds != null) {
            for (Long id : productIds) {
                products.add(productComponent.findById(id));
            }
        }
        return products;
    }
}
