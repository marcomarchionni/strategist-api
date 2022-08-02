package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.domain.Dividend;
import com.marcomarchionni.ibportfolio.models.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateStrategyDto;
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
    public List<Dividend> findByParams(@Valid DividendFindDto dividendFind) {
        return dividendService.findByParams(dividendFind);
    }

    @PutMapping
    public Dividend updateStrategyId(@RequestBody UpdateStrategyDto dividendUpdate) {
        return dividendService.updateStrategyId(dividendUpdate);
    }
}
