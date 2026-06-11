package com.fidelite.repository;

import com.fidelite.models.Settlement;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    // Get paginated settlements for a specific pool
    Page<Settlement> findByPoolSpId(UUID poolId, Pageable pageable);

    // Get all settlements for a pool (used for report generation)
    List<Settlement> findByPoolSpId(UUID poolId);

    // Check if a settlement for this pool+period already exists (prevents double-settlement)
    Optional<Settlement> findByPoolSpIdAndPeriodStart(UUID poolId, LocalDate periodStart);
}
