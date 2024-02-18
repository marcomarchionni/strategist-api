package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFind;
import com.marcomarchionni.strategistapi.dtos.request.UpdateName;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetail;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;
import com.marcomarchionni.strategistapi.services.StrategyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StrategyController implements StrategyApi {

    private final StrategyService strategyService;

    public List<StrategySummary> findByFilter(@Valid StrategyFind strategyFind) {
        return strategyService.findByFilter(strategyFind);
    }

    public StrategyDetail findById(@PathVariable Long id) {
        return strategyService.findById(id);
    }

    public StrategyDetail create(@RequestBody @Valid StrategyCreate strategyCreate) {
        return strategyService.create(strategyCreate);
    }

    public StrategyDetail updateName(@RequestBody @Valid UpdateName updateName) {
        return strategyService.updateName(updateName);
    }

    public void delete(@PathVariable Long id) {
        strategyService.deleteById(id);
    }
}
