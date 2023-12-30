package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendSummaryDto;
import com.marcomarchionni.ibportfolio.services.DividendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dividends")
public class DividendController {

    private final DividendService dividendService;

    @GetMapping
    public List<DividendSummaryDto> findByFilter(@Valid DividendFindDto dividendFind) {
        return dividendService.findByFilter(dividendFind);
    }

    @PutMapping
    public DividendSummaryDto updateStrategyId(@RequestBody @Valid UpdateStrategyDto dividendUpdate) {
        return dividendService.updateStrategyId(dividendUpdate);
    }
}
