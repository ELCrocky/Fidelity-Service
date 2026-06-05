package com.fidelite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.Tier;

@Repository
public interface TierRepository extends JpaRepository<Tier, Long> {

}
