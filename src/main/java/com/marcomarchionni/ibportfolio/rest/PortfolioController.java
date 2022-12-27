package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.model.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PortfolioListDto;
import com.marcomarchionni.ibportfolio.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public List<PortfolioListDto> findAll() {
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
