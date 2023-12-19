package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategySummaryDto;
import com.marcomarchionni.ibportfolio.services.StrategyService;
import com.marcomarchionni.ibportfolio.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/strategies")
public class StrategyController {

    private final StrategyService strategyService;
    private final UserService userService;

    @GetMapping
    public List<StrategySummaryDto> findByFilter(@Valid StrategyFindDto strategyFind) {
        User user = userService.getAuthenticatedUser();
        return strategyService.findByFilter(user, strategyFind);
    }

    @GetMapping("/{id}")
    public StrategyDetailDto findById(@PathVariable Long id) {
        User user = userService.getAuthenticatedUser();
        return strategyService.findByUserAndId(user, id);
    }

    @PostMapping
    public StrategyDetailDto create(@RequestBody @Valid StrategyCreateDto strategyCreateDto) {
        User user = userService.getAuthenticatedUser();
        return strategyService.create(user, strategyCreateDto);
    }

    @PutMapping
    public StrategyDetailDto updateName(@RequestBody @Valid UpdateNameDto updateNameDto) {
        User user = userService.getAuthenticatedUser();
        return strategyService.updateName(user, updateNameDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        User user = userService.getAuthenticatedUser();
        strategyService.deleteByUserAndId(user, id);
    }
}
