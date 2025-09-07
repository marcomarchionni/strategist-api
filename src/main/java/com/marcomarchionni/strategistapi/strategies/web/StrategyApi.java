package com.marcomarchionni.strategistapi.strategies.web;

import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetail;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

@Tag(name = "3. Strategies", description = "Create or edit user defined strategies")
@RequestMapping("/strategies")
@SecurityRequirement(name = "bearerAuth")
public interface StrategyApi {

        @GetMapping("")
        @Operation(summary = "Find all user's strategies")
        ApiResponse<StrategySummary> findAll(
                        @Parameter(description = "Number of records to skip for pagination") @RequestParam(defaultValue = "0") @Min(value = 0, message = "Skip must be greater or equal to 0") int skip,

                        @Parameter(description = "Number of records to return") @RequestParam(defaultValue = "10") @Min(value = 1, message = "Top must be greater or equal to 1") int top,

                        @Parameter(description = "Sort field and direction (e.g., 'name', 'name desc', 'portfolioName desc')") @RequestParam(required = false) String orderBy,

                        @Parameter(description = "Filter by strategy name (case-insensitive contains)") @RequestParam(required = false) String name,

                        @Parameter(description = "Filter by portfolio name (case-insensitive contains)") @RequestParam(required = false) String portfolioName);

        @GetMapping("/{id}")
        @Operation(summary = "Find strategy by id")
        StrategyDetail findById(@PathVariable Long id);

        @PostMapping
        @Operation(summary = "Create a new strategy")
        StrategyDetail create(@RequestBody @Valid StrategyCreate strategyCreate);

        @PutMapping
        @Operation(summary = "Update strategy name")
        StrategyDetail updateName(@RequestBody @Valid NameUpdate nameUpdate);

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete strategy by id")
        void delete(@PathVariable Long id);
}
