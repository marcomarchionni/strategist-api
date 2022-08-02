package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.domain.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.TradeListDto;
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
    public List<TradeListDto> findByParams(@Valid TradeFindDto tradeFind) {
        return tradeService.findByParams(tradeFind);
    }

    @PutMapping
    public Trade updateStrategyId(@RequestBody @Valid UpdateStrategyDto tradeUpdate) {
        return tradeService.updateStrategyId(tradeUpdate);
    }
}
