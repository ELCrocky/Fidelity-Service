package com.fidelite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
