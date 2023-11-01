package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionSummaryDto;
import com.marcomarchionni.ibportfolio.services.PositionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
public class PositionController {

    PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public List<PositionSummaryDto> findByFilter(@Valid PositionFindDto positionFind) {
        return positionService.findByFilter(positionFind);
    }

    @PutMapping
    public PositionSummaryDto updateStrategyId(@RequestBody @Valid UpdateStrategyDto positionUpdate) {
        return positionService.updateStrategyId(positionUpdate);
    }
}
