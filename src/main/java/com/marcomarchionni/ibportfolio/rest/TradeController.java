package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.TradeCriteriaDto;
import com.marcomarchionni.ibportfolio.services.TradeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/trades")
public class TradeController {

    TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping
    public List<Trade> findWithParameters(@Valid TradeCriteriaDto tradeCriteria) {

        return tradeService.findWithParameters(tradeCriteria);
    }

    @PutMapping
    public Trade updateStrategyId(@RequestBody Trade trade) {
        return tradeService.updateStrategyId(trade);
    }
}
