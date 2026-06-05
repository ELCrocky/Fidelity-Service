package com.fidelite.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.Reward;

@Repository
public interface RewardRepository extends JpaRepository<Reward, UUID> {

}
