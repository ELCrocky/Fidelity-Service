package com.fidelite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fidelite.models.EarningRule;

@Repository
public interface EarningRuleRepository extends JpaRepository<EarningRule, Long> {

}
