package com.marcomarchionni.ibportfolio.models.mapping;

import com.marcomarchionni.ibportfolio.models.domain.Portfolio;
import com.marcomarchionni.ibportfolio.models.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.PortfolioListDto;

public interface PortfolioMapper {

    Portfolio toEntity(PortfolioCreateDto portfolioCreateDto);
    PortfolioListDto toPortfolioListDto(Portfolio portfolio);
    PortfolioDetailDto toPortfolioDetailDto(Portfolio portfolio);
}
