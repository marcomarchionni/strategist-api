package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.request.TradeFind;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummary;
import com.marcomarchionni.strategistapi.services.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TradeController implements TradeApi {

    private final TradeService tradeService;

    public List<TradeSummary> findByFilter(@Valid TradeFind tradeFind) {
        return tradeService.findByFilter(tradeFind);
    }

    public TradeSummary updateStrategyId(@RequestBody @Valid StrategyAssign strategyAssign) {
        return tradeService.updateStrategyId(strategyAssign);
    }
}
