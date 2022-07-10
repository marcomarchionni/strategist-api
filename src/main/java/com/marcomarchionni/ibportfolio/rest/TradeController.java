package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.EntityNotFoundErrorResponse;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.services.TradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TradeController {

    TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/trades")
    public List<Trade> getTrades() {

        return tradeService.findAll();
    }

    @PutMapping("/trades")
    public Trade updateStrategyId(@RequestBody Trade trade) {
        return tradeService.updateStrategyId(trade);
    }
}
