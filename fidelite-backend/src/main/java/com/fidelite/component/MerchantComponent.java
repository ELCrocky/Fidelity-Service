package com.fidelite.component;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Merchant;
import com.fidelite.repository.MerchantRepository;

import lombok.RequiredArgsConstructor;

// Data-access component for Merchant entities.
@Component
@RequiredArgsConstructor
public class MerchantComponent {

    private final MerchantRepository merchantRepository;

    public Merchant save(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    // Finds a merchant by UUID, throwing a typed exception if absent.
    public Merchant findById(UUID id) throws NotFoundElementException {
        return merchantRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("merchant with id [%s] not found", id)));
    }

    // Settlement starting point: merchants belonging to the given pool.
    public List<Merchant> findByPoolId(UUID poolId) {
        return merchantRepository.findByPoolSpId(poolId);
    }

    // Resolves a merchant by API key when a kiosk connects.
    public Merchant findByApiKey(String apiKey) throws NotFoundElementException {
        return merchantRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new NotFoundElementException(
                        String.format("merchant with apiKey [%s] not found", apiKey)));
    }

    // Returns merchants in the given pool filtered by region.
    public List<Merchant> findByPoolIdAndRegion(UUID poolId, String region) {
        return merchantRepository.findByPoolSpIdAndRegion(poolId, region);
    }

    // Returns all merchants.
    public List<Merchant> findAll() {
        return merchantRepository.findAll();
    }

    public void deleteById(UUID id) {
        merchantRepository.deleteById(id);
    }
}
