package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.services.TradeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/trades")
public class TradeController {

    TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping
    public List<Trade> findWithParameters(@RequestParam (value = "startDate", required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                          @RequestParam (value = "endDate", required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                          @RequestParam (value = "tagged", required = false) Boolean tagged,
                                          @RequestParam (value = "symbol", required = false) String symbol,
                                          @RequestParam (value = "assetCategory", required = false) String assetCategory
                                 ) {

        return tradeService.findWithParameters(startDate, endDate, tagged, symbol, assetCategory);
    }

    @PutMapping
    public Trade updateStrategyId(@RequestBody Trade trade) {
        return tradeService.updateStrategyId(trade);
    }
}
