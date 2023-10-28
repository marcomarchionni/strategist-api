package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyListDto;

import java.util.List;

public interface StrategyService {

    List<StrategyListDto> findByFilter(StrategyFindDto strategyFind);

    StrategyDetailDto updateName(UpdateNameDto updateNameDto);

    StrategyDetailDto create(StrategyCreateDto strategyCreateDto);

    void deleteById(Long id);

    StrategyDetailDto findById(Long id);
}
