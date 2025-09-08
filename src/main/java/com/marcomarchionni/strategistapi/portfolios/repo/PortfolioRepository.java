package com.marcomarchionni.strategistapi.portfolios.repo;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository
    extends JpaRepository<Portfolio, Long>, JpaSpecificationExecutor<Portfolio> {
  Optional<Portfolio> findByAccountIdAndName(String accountId, String name);

  boolean existsByAccountIdAndName(String accountId, String portfolioName);

  List<Portfolio> findAllByAccountId(String accountId);

  Page<Portfolio> findAllBy(Specification<Portfolio> spec, Pageable pageable);

  Optional<Portfolio> findByIdAndAccountId(Long id, String accountId);

  void deleteByAccountId(String accountId);

  int countByAccountId(String accountId);
}
