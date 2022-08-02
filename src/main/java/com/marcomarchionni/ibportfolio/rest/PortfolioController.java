package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.domain.Portfolio;
import com.marcomarchionni.ibportfolio.models.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<Portfolio> findAll() {
        return portfolioService.findAll();
    }

    @GetMapping("/{id}")
    public Portfolio findById(@PathVariable Long id) {
        return portfolioService.findById(id);
    }

    @PostMapping
    public Portfolio create(@RequestBody @Valid PortfolioCreateDto portfolioCreate) {
        return portfolioService.create(portfolioCreate);
    }

    @PutMapping
    public Portfolio updateName(@RequestBody @Valid UpdateNameDto updateName) {
        return portfolioService.updateName(updateName);
    }

    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        portfolioService.deleteById(id);
    }
}
