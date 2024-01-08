package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateNameDto;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummaryDto;
import com.marcomarchionni.strategistapi.services.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public List<PortfolioSummaryDto> findAllByUser() {
        return portfolioService.findAll();
    }

    @GetMapping("/{id}")
    public PortfolioDetailDto findById(@PathVariable Long id) {
        return portfolioService.findById(id);
    }

    @PostMapping
    public PortfolioDetailDto create(@RequestBody @Valid PortfolioCreateDto portfolioCreate) {
        return portfolioService.create(portfolioCreate);
    }

    @PutMapping
    public PortfolioDetailDto updateName(@RequestBody @Valid UpdateNameDto updateName) {
        return portfolioService.updateName(updateName);
    }

    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        portfolioService.deleteById(id);
    }
}
