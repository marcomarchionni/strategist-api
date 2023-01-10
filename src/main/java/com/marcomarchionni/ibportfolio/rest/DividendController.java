package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.model.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.DividendListDto;
import com.marcomarchionni.ibportfolio.services.DividendService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dividends")
public class DividendController {

    DividendService dividendService;

    public DividendController(DividendService dividendService) {
        this.dividendService = dividendService;
    }

    @GetMapping
    public List<DividendListDto> findByFilter(@Valid DividendFindDto dividendFind) {
        return dividendService.findByFilter(dividendFind);
    }

    @PutMapping
    public DividendListDto updateStrategyId(@RequestBody UpdateStrategyDto dividendUpdate) {
        return dividendService.updateStrategyId(dividendUpdate);
    }
}
