package com.fidelite.component;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.LoyaltyCard;
import com.fidelite.repository.LoyaltyCardRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoyaltyCardComponent {

    private final LoyaltyCardRepository loyaltyCardRepository;

}
