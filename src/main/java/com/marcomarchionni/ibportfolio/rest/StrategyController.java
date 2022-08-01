package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.StrategyFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateNameDto;
import com.marcomarchionni.ibportfolio.services.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<Strategy> findByParams(@Valid StrategyFindDto strategyFind) {
        return strategyService.findByParams(strategyFind);
    }

    @PostMapping
    public Strategy create(@RequestBody @Valid StrategyCreateDto strategyCreateDto) {
        return strategyService.create(strategyCreateDto);
    }

    @PutMapping
    public Strategy updateName(@RequestBody @Valid UpdateNameDto updateNameDto) {
        return strategyService.updateName(updateNameDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        strategyService.deleteById(id);
    }
}
