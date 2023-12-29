package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioSummaryDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PortfolioService {
    List<PortfolioSummaryDto> findAll();

    PortfolioDetailDto findById(Long id);

    PortfolioDetailDto create(PortfolioCreateDto portfolioCreateDto);

    void deleteById(Long id);

    PortfolioDetailDto updateName(@Valid UpdateNameDto dto);
}
