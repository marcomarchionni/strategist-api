package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.services.TradeService;
import com.marcomarchionni.ibportfolio.validation.NotBefore1970;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
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
    public List<Trade> findWithParameters(
            @RequestParam (value = "tradeDateFrom", required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                @NotBefore1970 LocalDate tradeDateFrom,
            @RequestParam (value = "tradeDateTo", required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                @PastOrPresent LocalDate tradeDateTo,
            @RequestParam (value = "tagged", required = false) Boolean tagged,
            @RequestParam (value = "symbol", required = false)
                @Size(max=20) String symbol,
            @RequestParam (value = "assetCategory", required = false) String assetCategory) {

        return tradeService.findWithParameters(tradeDateFrom, tradeDateTo, tagged, symbol, assetCategory);
    }

    @PutMapping
    public Trade updateStrategyId(@RequestBody Trade trade) {
        return tradeService.updateStrategyId(trade);
    }
}
