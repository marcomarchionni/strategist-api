package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import jakarta.validation.Valid;

import java.util.List;

public interface PortfolioService {
    List<PortfolioSummary> findAll();

    PortfolioDetail findById(Long id);

    PortfolioDetail create(PortfolioSave portfolioSave);

    void deleteById(Long id);

    PortfolioDetail updateName(@Valid NameUpdate dto);
}
