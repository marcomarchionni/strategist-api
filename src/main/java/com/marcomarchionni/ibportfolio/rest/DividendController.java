package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.dtos.DividendCriteriaDto;
import com.marcomarchionni.ibportfolio.services.DividendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<Dividend> findWithCriteria(@Valid DividendCriteriaDto dividendCriteria) {
        return dividendService.findWithCriteria(dividendCriteria);
    }

    @PutMapping
    public Dividend updateStrategyId(@RequestBody Dividend dividend) {
        return dividendService.updateStrategyId(dividend);
    }
}
