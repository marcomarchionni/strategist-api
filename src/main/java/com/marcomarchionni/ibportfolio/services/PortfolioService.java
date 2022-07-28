package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Portfolio;

import java.util.List;

public interface PortfolioService {

    List<Portfolio> findAll();

    Portfolio findById(Long id);

    Portfolio save(Portfolio portfolio);

    Portfolio updatePortfolioName(Portfolio portfolio);

    void deleteById(Long id);
}
