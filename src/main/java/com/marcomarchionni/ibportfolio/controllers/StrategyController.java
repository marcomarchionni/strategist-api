package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategySummaryDto;
import com.marcomarchionni.ibportfolio.services.StrategyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/strategies")
public class StrategyController {

    private final StrategyService strategyService;

    @GetMapping
    public List<StrategySummaryDto> findByFilter(@Valid StrategyFindDto strategyFind) {
        return strategyService.findByFilter(strategyFind);
    }

    @GetMapping("/{id}")
    public StrategyDetailDto findById(@PathVariable Long id) {
        return strategyService.findById(id);
    }

    @PostMapping
    public StrategyDetailDto create(@RequestBody @Valid StrategyCreateDto strategyCreateDto) {
        return strategyService.create(strategyCreateDto);
    }

    @PutMapping
    public StrategyDetailDto updateName(@RequestBody @Valid UpdateNameDto updateNameDto) {
        return strategyService.updateName(updateNameDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        strategyService.deleteById(id);
    }
}
