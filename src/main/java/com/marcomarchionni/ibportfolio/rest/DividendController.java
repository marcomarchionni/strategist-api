package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.services.DividendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dividends")
public class DividendController {

    DividendService dividendService;

    @Autowired
    public DividendController(DividendService dividendService) {
        this.dividendService = dividendService;
    }

    @GetMapping
    public List<Dividend> findWithParameters(@RequestParam(value = "exDeteFrom", required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate exDeteFrom,
                                             @RequestParam (value = "exDateTo", required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate exDateTo,
                                             @RequestParam(value = "payDateFrom", required = false)
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate payDateFrom,
                                             @RequestParam (value = "payDateTo", required = false)
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate payDateTo,
                                             @RequestParam (value = "tagged", required = false) Boolean tagged,
                                             @RequestParam (value = "symbol", required = false) String symbol
    ) {
        return dividendService.findWithParameters(exDeteFrom, exDateTo, payDateFrom, payDateTo, tagged, symbol);
    }

    @PutMapping
    public Dividend updateStrategyId(@RequestBody Dividend dividend) {
        return dividendService.updateStrategyId(dividend);
    }
}
