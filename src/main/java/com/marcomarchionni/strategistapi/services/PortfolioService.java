package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateNameDto;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummaryDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PortfolioService {
    List<PortfolioSummaryDto> findAll();

    PortfolioDetailDto findById(Long id);

    PortfolioDetailDto create(PortfolioCreateDto portfolioCreateDto);

    void deleteById(Long id);

    PortfolioDetailDto updateName(@Valid UpdateNameDto dto);
}
