package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioSummaryDto;

public interface PortfolioMapper {

    PortfolioSummaryDto toPortfolioListDto(Portfolio portfolio);

    PortfolioDetailDto toPortfolioDetailDto(Portfolio portfolio);
}
