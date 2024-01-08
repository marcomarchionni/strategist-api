package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.DividendFindDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummaryDto;
import com.marcomarchionni.strategistapi.services.DividendService;
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
