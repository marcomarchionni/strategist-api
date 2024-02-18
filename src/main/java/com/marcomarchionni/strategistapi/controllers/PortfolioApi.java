package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.PortfolioCreate;
import com.marcomarchionni.strategistapi.dtos.request.UpdateName;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "3. Portfolios", description = "Create or edit user defined portfolios.")
@RequestMapping("/portfolios")
public interface PortfolioApi {
    @GetMapping
    @Operation(summary = "Find all user's portfolios")
    List<PortfolioSummary> findAllByUser();

    @GetMapping("/{id}")
    @Operation(summary = "Find portfolio by id")
    PortfolioDetail findById(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "Create a new portfolio")
    PortfolioDetail create(@RequestBody @Valid PortfolioCreate portfolioCreate);

    @PutMapping
    @Operation(summary = "Update portfolio name")
    PortfolioDetail updateName(@RequestBody @Valid UpdateName updateName);

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete portfolio by id")
    void deletePortfolio(@PathVariable Long id);
}
