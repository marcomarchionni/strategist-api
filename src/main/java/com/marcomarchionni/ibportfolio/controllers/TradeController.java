package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.TradeListDto;
import com.marcomarchionni.ibportfolio.services.TradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<TradeListDto> findByFilter(@Valid TradeFindDto tradeFind) {
        return tradeService.findByFilter(tradeFind);
    }

    @PutMapping
    public TradeListDto updateStrategyId(@RequestBody @Valid UpdateStrategyDto tradeUpdate) {
        return tradeService.updateStrategyId(tradeUpdate);
    }
}
