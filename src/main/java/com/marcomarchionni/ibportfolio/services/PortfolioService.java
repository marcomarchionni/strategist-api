package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.PortfolioListDto;

import java.util.List;

public interface PortfolioService {

    List<PortfolioListDto> findAll();

    PortfolioDetailDto findById(Long id);

    PortfolioDetailDto create(PortfolioCreateDto portfolio);

    PortfolioDetailDto updateName(UpdateNameDto updateNameDto);

    void deleteById(Long id);
}
