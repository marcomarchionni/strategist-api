package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;

import java.util.List;

public interface PortfolioService extends EntityService<PortfolioSave, PortfolioSummary> {
    List<PortfolioSummary> findAll(FindAllReq findReq);

    PortfolioDetail findById(Long id);

    PortfolioSummary create(PortfolioSave portfolioSave);

    PortfolioSummary update(PortfolioSave portfolioSave);

    void deleteById(Long id);

    ApiResponse<PortfolioSummary> findAllWithCount(FindAllReq findReq);
}
