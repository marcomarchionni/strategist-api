package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.StrategyCreateDto;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFindDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateNameDto;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetailDto;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummaryDto;
import com.marcomarchionni.strategistapi.services.StrategyService;
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
