package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.TradeCriteriaDto;
import com.marcomarchionni.ibportfolio.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/trades")
public class TradeController {

    TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping
    public List<Trade> findWithCriteria(@Valid TradeCriteriaDto tradeCriteria) {
        return tradeService.findWithCriteria(tradeCriteria);
    }

    @PutMapping
    public Trade updateStrategyId(@RequestBody Trade trade) {
        return tradeService.updateStrategyId(trade);
    }
}
