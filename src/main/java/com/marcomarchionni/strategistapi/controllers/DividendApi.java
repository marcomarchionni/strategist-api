package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.DividendFind;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "7. Dividends", description = "List dividends or assign them to a strategy")
@RequestMapping("/dividends")
@SecurityRequirement(name = "bearerAuth")
public interface DividendApi {
    @GetMapping
    @Operation(summary = "Find dividends by filter",
            parameters = {
                    @Parameter(name = "exDateAfter", description = "exDate after", example = "2021-01-01"),
                    @Parameter(name = "exDateBefore", description = "exDate before", example = "2021-12-31"),
                    @Parameter(name = "payDateAfter", description = "payDate after", example = "2021-01-01"),
                    @Parameter(name = "payDateBefore", description = "payDate before", example = "2021-12-31"),
                    @Parameter(name = "symbol", description = "Symbol", example = "AAPL"),
                    @Parameter(name = "tagged", description = "Dividend assigned to a strategy", example = "false"),
            })
    @Parameter(name = "dividendFind", hidden = true)
    List<DividendSummary> findByFilter(@Valid DividendFind dividendFind);

    @PutMapping
    @Operation(summary = "Assign dividend to a strategy. Use strategyId = null to unassign dividend from any strategy.")
    DividendSummary updateStrategyId(@RequestBody @Valid StrategyAssign dividendUpdate);
}
