package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioCreate;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.services.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolios/")
public class PortfolioController implements PortfolioApi {

    private final PortfolioService portfolioService;

    @GetMapping
    public ApiResponse<PortfolioSummary> findAll(@RequestParam(value = "$inlinecount", required = false) String inlineCount) {
        var results = portfolioService.findAll();
        var count = results.size();
        return ApiResponse.<PortfolioSummary>builder().result(results).count(count).build();
    }

    @GetMapping("/{id}")
    public PortfolioDetail findById(@PathVariable Long id) {
        return portfolioService.findById(id);
    }

    @PostMapping
    public PortfolioDetail create(@RequestBody @Valid PortfolioCreate portfolioCreate) {
        return portfolioService.create(portfolioCreate);
    }

    @PutMapping
    public PortfolioDetail updateName(@RequestBody @Valid NameUpdate nameUpdate) {
        return portfolioService.updateName(nameUpdate);
    }

    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        portfolioService.deleteById(id);
    }
}
