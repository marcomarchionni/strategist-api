package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.request.TradeFind;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "5. Trades", description = "List trades or assign them to a strategy")
@RequestMapping("/trades")
@SecurityRequirement(name = "bearerAuth")
public interface TradeApi {

    @GetMapping
    @Operation(summary = "Find trades by filter",
            parameters = {
                    @Parameter(name = "tradeDateAfter", description = "Trade date after", example = "2021-01-01", in
                            = ParameterIn.QUERY, schema = @Schema(type = "string", format = "date")),
                    @Parameter(name = "tradeDateBefore", description = "Trade date before", example = "2024-02-02",
                            in = ParameterIn.QUERY, schema = @Schema(type = "string", format = "date")),
                    @Parameter(name = "tagged", description = "Trade assigned to a strategy", example = "false", in =
                            ParameterIn.QUERY, schema = @Schema(type = "boolean")),
                    @Parameter(name = "symbol", description = "Symbol", example = "AAPL", in = ParameterIn.QUERY,
                            schema = @Schema(type = "string")),
                    @Parameter(name = "assetCategory", description = "Asset category. Allowed values: STK, OPT, FUT, " +
                            "CASH", example = "STK", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            })
    @Parameter(name = "tradeFind", hidden = true)
    List<TradeSummary> findByFilter(@Valid TradeFind tradeFind);

    @PutMapping
    @Operation(summary = "Assign a strategy to a trade. Make the trade part of a strategy")
    TradeSummary updateStrategyId(@RequestBody @Valid StrategyAssign strategyAssign);
}
