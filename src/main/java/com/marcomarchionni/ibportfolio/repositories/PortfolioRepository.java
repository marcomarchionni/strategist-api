package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByName(String portfolioName);

    boolean existsByName(String portfolioName);
}
