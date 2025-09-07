package com.marcomarchionni.strategistapi.portfolios.service;

import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.services.EntityService;

public interface PortfolioService extends EntityService<PortfolioSave, PortfolioSummary> {
    ApiResponse<PortfolioSummary> findAllWithCount(FindAllReq findReq);

    PortfolioDetail findById(Long portfolioId);

    void deleteById(Long portfolioId);
}
