package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;

public interface PortfolioService extends EntityService<PortfolioSave, PortfolioSummary> {
    ApiResponse<PortfolioSummary> findAllWithCount(FindAllReq findReq);

    PortfolioDetail findById(Long portfolioId);

    void deleteById(Long portfolioId);
}