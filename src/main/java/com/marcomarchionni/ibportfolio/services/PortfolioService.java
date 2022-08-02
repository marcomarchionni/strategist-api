package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.domain.Portfolio;
import com.marcomarchionni.ibportfolio.models.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateNameDto;

import java.util.List;

public interface PortfolioService {

    List<Portfolio> findAll();

    Portfolio findById(Long id);

    Portfolio create(PortfolioCreateDto portfolio);

    Portfolio updateName(UpdateNameDto updateNameDto);

    void deleteById(Long id);
}
