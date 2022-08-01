package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.StrategyFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateNameDto;

import java.util.List;

public interface StrategyService {

    List<Strategy> findByParams(StrategyFindDto strategyFind);

    Strategy updateName(UpdateNameDto updateNameDto);

    Strategy create(StrategyCreateDto strategyCreateDto);

    void deleteById(Long id);
}
