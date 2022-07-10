package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
