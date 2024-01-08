package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.StrategyCreateDto;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFindDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateNameDto;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetailDto;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummaryDto;

import java.util.List;

public interface StrategyService {

    List<StrategySummaryDto> findByFilter(StrategyFindDto strategyFind);

    StrategyDetailDto updateName(UpdateNameDto updateNameDto);

    void deleteById(Long id);

    StrategyDetailDto findById(Long id);

    StrategyDetailDto create(StrategyCreateDto strategyCreateDto);
}
