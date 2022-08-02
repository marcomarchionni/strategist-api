package com.marcomarchionni.ibportfolio.rest;

import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyListDto;
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
    public List<StrategyListDto> findByParams(@Valid StrategyFindDto strategyFind) {
        return strategyService.findByParams(strategyFind);
    }

    @GetMapping("/{id}")
    public StrategyDetailDto findById(@PathVariable Long id) {
        return strategyService.findById(id);
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
