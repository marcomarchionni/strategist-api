package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioSummaryDto;
import com.marcomarchionni.ibportfolio.services.PortfolioService;
import com.marcomarchionni.ibportfolio.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final UserService userService;

    @GetMapping
    public List<PortfolioSummaryDto> findAllByUser() {
        User user = userService.getAuthenticatedUser();
        return portfolioService.findAllByUser(user);
    }

    @GetMapping("/{id}")
    public PortfolioDetailDto findById(@PathVariable Long id) {
        User user = userService.getAuthenticatedUser();
        return portfolioService.findByUserAndId(user, id);
    }

    @PostMapping
    public PortfolioDetailDto create(@RequestBody @Valid PortfolioCreateDto portfolioCreate) {
        User user = userService.getAuthenticatedUser();
        return portfolioService.create(user, portfolioCreate);
    }

    @PutMapping
    public PortfolioDetailDto updateName(@RequestBody @Valid UpdateNameDto updateName) {
        User user = userService.getAuthenticatedUser();
        return portfolioService.updateName(user, updateName);
    }

    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        User user = userService.getAuthenticatedUser();
        portfolioService.deleteByUserAndId(user, id);
    }
}
