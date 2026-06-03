package com.fidelite.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.fidelite.enums.Ttype;
import com.fidelite.models.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
    List<History> findByFideliteIdFidelite(String idFidelite);

    List<History> findByTransactionType(Ttype type);

    List<History> findByTransactionDateBetween(LocalDateTime start, LocalDateTime end);
}
