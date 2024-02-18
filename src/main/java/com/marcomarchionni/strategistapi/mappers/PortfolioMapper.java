package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;

public interface PortfolioMapper {

    PortfolioSummary toPortfolioSummaryDto(Portfolio portfolio);

    PortfolioDetail toPortfolioDetailDto(Portfolio portfolio);
}
