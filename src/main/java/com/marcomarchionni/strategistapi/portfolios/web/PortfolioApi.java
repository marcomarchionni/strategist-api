package com.marcomarchionni.strategistapi.portfolios.web;

import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;

import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

@Tag(name = "2. Portfolios", description = "Create or edit user defined portfolios.")
@RequestMapping("/portfolios")
@SecurityRequirement(name = "bearerAuth")
public interface PortfolioApi {
    @GetMapping("")
    @Operation(summary = "Find all user's portfolios")
    ApiResponse<PortfolioSummary> findAll(
            @Parameter(description = "Number of records to skip for pagination") @RequestParam(defaultValue = "0") @Min(value = 0, message = "Skip must be greater or equal to 0") int skip,

            @Parameter(description = "Number of records to return") @RequestParam(defaultValue = "10") @Min(value = 1, message = "Top must be greater or equal to 1") int top,

            @Parameter(description = "Sort field and direction (e.g., 'name', 'name desc')") @RequestParam(required = false) String orderBy,

            @Parameter(description = "Filter by portfolio name (case-insensitive contains)") @RequestParam(required = false) String name,

            @Parameter(description = "Filter by portfolio description (case-insensitive contains)") @RequestParam(required = false) String description,

            @Parameter(description = "Filter portfolios created after this date (YYYY-MM-DD)") @RequestParam(required = false) String createdAfter,

            @Parameter(description = "Filter portfolios created before this date (YYYY-MM-DD)") @RequestParam(required = false) String createdBefore);

    @GetMapping("/{id}")
    @Operation(summary = "Find portfolio by id")
    PortfolioDetail findById(@PathVariable Long id);

    @PostMapping("")
    @Operation(summary = "Create a new portfolio")
    PortfolioSummary createPortfolio(@RequestBody @Valid PortfolioSave portfolioSave);

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete portfolio by id")
    void deletePortfolio(@PathVariable Long id);

    @PutMapping("/{id}")
    @Operation(summary = "Update portfolio by id")
    PortfolioSummary updatePortfolio(@PathVariable Long id, @RequestBody @Valid PortfolioSave portfolioSave);
}
