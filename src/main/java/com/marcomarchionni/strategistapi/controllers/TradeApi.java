package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.TradeFind;
import com.marcomarchionni.strategistapi.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "5. Trades", description = "List trades or assign them to a strategy")
@RequestMapping("/trades")
public interface TradeApi {

    @GetMapping
    @Operation(summary = "Find trades by filter")
    List<TradeSummary> findByFilter(@Valid TradeFind tradeFind);

    @PutMapping
    @Operation(summary = "Assign trade to a strategy")
    TradeSummary updateStrategyId(@RequestBody @Valid UpdateStrategyDto tradeUpdate);
}
