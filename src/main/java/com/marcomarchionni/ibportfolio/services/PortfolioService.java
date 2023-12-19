package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioSummaryDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PortfolioService {
    List<PortfolioSummaryDto> findAllByUser(User user);

    PortfolioDetailDto findByUserAndId(User user, Long id);

    PortfolioDetailDto create(User user, PortfolioCreateDto portfolioCreateDto);

    void deleteByUserAndId(User user, Long id);

    PortfolioDetailDto updateName(User user, @Valid UpdateNameDto dto);
}
