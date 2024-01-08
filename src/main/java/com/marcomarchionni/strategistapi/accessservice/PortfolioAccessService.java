package com.marcomarchionni.strategistapi.accessservice;

import com.marcomarchionni.strategistapi.domain.Portfolio;

import java.util.List;
import java.util.Optional;

public interface PortfolioAccessService {
    List<Portfolio> findAll();

    boolean existsByName(String name);

    Optional<Portfolio> findById(Long id);

    Portfolio save(Portfolio portfolio);

    void delete(Portfolio portfolio);
}
