package com.fidelite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.Redemption;

@Repository
public interface RedemptionRepository extends JpaRepository<Redemption, Long> {

}
