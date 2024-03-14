package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioCreate;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import jakarta.validation.Valid;

import java.util.List;

public interface PortfolioService {
    List<PortfolioSummary> findAll();

    PortfolioDetail findById(Long id);

    PortfolioDetail create(PortfolioCreate portfolioCreate);

    void deleteById(Long id);

    PortfolioDetail updateName(@Valid NameUpdate dto);
}
