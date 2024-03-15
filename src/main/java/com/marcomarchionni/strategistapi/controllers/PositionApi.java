package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.PositionFind;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.response.PositionSummary;
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

@Tag(name = "6. Positions", description = "List positions or assign them to a strategy")
@RequestMapping("/positions")
@SecurityRequirement(name = "bearerAuth")
public interface PositionApi {
    @GetMapping
    @Operation(summary = "Find positions by filter",
            parameters = {
                    @Parameter(name = "tagged", description = "Position assigned to a strategy", example = "false"),
                    @Parameter(name = "symbol", description = "Symbol", example = "AAPL"),
                    @Parameter(name = "assetCategory", description = "Asset category. Allowed values: STK, OPT, FUT, " +
                            "CASH", example = "STK")
            })
    @Parameter(name = "positionFind", hidden = true)
    List<PositionSummary> findByFilter(@Valid PositionFind positionFind);

    @PutMapping
    @Operation(summary = "Assign position to a strategy. Use strategyId = null to unassign position from any strategy.")
    PositionSummary updateStrategyId(@RequestBody @Valid StrategyAssign positionUpdate);
}
