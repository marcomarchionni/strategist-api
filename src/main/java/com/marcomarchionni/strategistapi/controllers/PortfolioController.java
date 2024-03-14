package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioCreate;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.services.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PortfolioController implements PortfolioApi {

    private final PortfolioService portfolioService;

    public List<PortfolioSummary> findAll() {
        return portfolioService.findAll();
    }

    public PortfolioDetail findById(@PathVariable Long id) {
        return portfolioService.findById(id);
    }

    public PortfolioDetail create(@RequestBody @Valid PortfolioCreate portfolioCreate) {
        return portfolioService.create(portfolioCreate);
    }

    public PortfolioDetail updateName(@RequestBody @Valid NameUpdate nameUpdate) {
        return portfolioService.updateName(nameUpdate);
    }

    public void deletePortfolio(@PathVariable Long id) {
        portfolioService.deleteById(id);
    }
}
