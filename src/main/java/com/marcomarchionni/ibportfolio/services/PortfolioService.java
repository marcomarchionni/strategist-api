package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Portfolio;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateNameDto;

import java.util.List;

public interface PortfolioService {

    List<Portfolio> findAll();

    Portfolio findById(Long id);

    Portfolio save(Portfolio portfolio);

    Portfolio updateName(UpdateNameDto updateNameDto);

    void deleteById(Long id);
}
