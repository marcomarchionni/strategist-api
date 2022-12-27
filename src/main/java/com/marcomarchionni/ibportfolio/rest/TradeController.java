package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.model.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.TradeListDto;
import com.marcomarchionni.ibportfolio.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
