package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByAccountIdAndName(String accountId, String name);

    boolean existsByNameAndAccountId(String portfolioName, String accountId);

    List<Portfolio> findAllByAccountId(String accountId);
}
