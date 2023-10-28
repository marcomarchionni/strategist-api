package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioListDto;

public interface PortfolioMapper {

    Portfolio toEntity(PortfolioCreateDto portfolioCreateDto);

    PortfolioListDto toPortfolioListDto(Portfolio portfolio);

    PortfolioDetailDto toPortfolioDetailDto(Portfolio portfolio);
}
