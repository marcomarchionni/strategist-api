package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.PositionFind;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.response.PositionSummary;
import com.marcomarchionni.strategistapi.services.PositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PositionController implements PositionApi {

    private final PositionService positionService;

    public List<PositionSummary> findByFilter(@Valid PositionFind positionFind) {
        return positionService.findByFilter(positionFind);
    }

    public PositionSummary updateStrategyId(@RequestBody @Valid StrategyAssign positionUpdate) {
        return positionService.updateStrategyId(positionUpdate);
    }
}
