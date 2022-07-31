package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.Portfolio;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateNameDto;
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
    public List<Portfolio> findPortfolios() {
        return portfolioService.findAll();
    }

    @GetMapping("/{id}")
    public Portfolio findPortfolio(@PathVariable Long id) {
        return portfolioService.findById(id);
    }

    @PostMapping
    public Portfolio createPortfolio(@RequestBody @Valid Portfolio portfolio) {
        return portfolioService.save(portfolio);
    }

    @PutMapping
    public Portfolio updatePortfolioName(@RequestBody @Valid UpdateNameDto updateName) {
        return portfolioService.updateName(updateName);
    }


    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        portfolioService.deleteById(id);
    }
}
