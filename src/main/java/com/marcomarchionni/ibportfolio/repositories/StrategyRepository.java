package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.models.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Long> {
}
