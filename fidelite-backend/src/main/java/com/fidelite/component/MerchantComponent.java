package com.fidelite.component;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Merchant;
import com.fidelite.repository.MerchantRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MerchantComponent {

    private final MerchantRepository merchantRepository;

}
