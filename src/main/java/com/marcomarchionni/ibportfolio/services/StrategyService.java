package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategySummaryDto;

import java.util.List;

public interface StrategyService {

    List<StrategySummaryDto> findByFilter(StrategyFindDto strategyFind);

    StrategyDetailDto updateName(UpdateNameDto updateNameDto);

    void deleteById(Long id);

    StrategyDetailDto findById(Long id);

    StrategyDetailDto create(StrategyCreateDto strategyCreateDto);
}
