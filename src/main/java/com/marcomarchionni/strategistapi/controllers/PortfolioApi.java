package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioCreate;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "2. Portfolios", description = "Create or edit user defined portfolios.")
@RequestMapping("/portfolios")
@SecurityRequirement(name = "bearerAuth")
public interface PortfolioApi {
    @GetMapping
    @Operation(summary = "Find all user's portfolios")
    ApiResponse<PortfolioSummary> findAll(String inlineCount);

    @GetMapping("/{id}")
    @Operation(summary = "Find portfolio by id")
    PortfolioDetail findById(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "Create a new portfolio")
    PortfolioDetail create(@RequestBody @Valid PortfolioCreate portfolioCreate);

    @PutMapping
    @Operation(summary = "Update portfolio name")
    PortfolioDetail updateName(@RequestBody @Valid NameUpdate nameUpdate);

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete portfolio by id")
    void deletePortfolio(@PathVariable Long id);
}
