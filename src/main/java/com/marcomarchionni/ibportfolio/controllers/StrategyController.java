package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyListDto;
import com.marcomarchionni.ibportfolio.services.StrategyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/strategies")
public class StrategyController {

    StrategyService strategyService;

    @Autowired
    public StrategyController(StrategyService strategyService) {
        this.strategyService = strategyService;
    }

    @GetMapping
    public List<StrategyListDto> findByFilter(@Valid StrategyFindDto strategyFind) {
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
