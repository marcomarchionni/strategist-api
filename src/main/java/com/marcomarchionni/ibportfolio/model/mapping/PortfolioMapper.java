package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.Portfolio;
import com.marcomarchionni.ibportfolio.model.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PortfolioListDto;

public interface PortfolioMapper {

    Portfolio toEntity(PortfolioCreateDto portfolioCreateDto);
    PortfolioListDto toPortfolioListDto(Portfolio portfolio);
    PortfolioDetailDto toPortfolioDetailDto(Portfolio portfolio);
}
