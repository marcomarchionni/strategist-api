package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummaryDto;

public interface PortfolioMapper {

    PortfolioSummaryDto toPortfolioSummaryDto(Portfolio portfolio);

    PortfolioDetailDto toPortfolioDetailDto(Portfolio portfolio);
}
