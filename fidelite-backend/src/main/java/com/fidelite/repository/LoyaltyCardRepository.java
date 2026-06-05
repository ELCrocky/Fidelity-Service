package com.fidelite.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.LoyaltyCard;

@Repository
public interface LoyaltyCardRepository extends JpaRepository<LoyaltyCard, UUID> {

}
