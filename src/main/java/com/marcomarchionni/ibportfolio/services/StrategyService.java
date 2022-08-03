package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyListDto;

import java.util.List;

public interface StrategyService {

    List<StrategyListDto> findByParams(StrategyFindDto strategyFind);

    StrategyDetailDto updateName(UpdateNameDto updateNameDto);

    StrategyDetailDto create(StrategyCreateDto strategyCreateDto);

    void deleteById(Long id);

    StrategyDetailDto findById(Long id);
}
