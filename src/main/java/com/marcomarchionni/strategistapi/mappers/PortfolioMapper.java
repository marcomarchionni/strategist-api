package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;

public interface PortfolioMapper {

    PortfolioSummary portfolioToPortfolioSummary(Portfolio portfolio);

    PortfolioDetail toPortfolioDetailDto(Portfolio portfolio);

    void mergePortfolioSaveToPortfolio(PortfolioSave portfolioSave, Portfolio portfolio);
}
