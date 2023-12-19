package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategySummaryDto;

import java.util.List;

public interface StrategyService {

    List<StrategySummaryDto> findByFilter(User user, StrategyFindDto strategyFind);

    StrategyDetailDto updateName(User user, UpdateNameDto updateNameDto);

    void deleteByUserAndId(User user, Long id);

    StrategyDetailDto findByUserAndId(User user, Long id);

    StrategyDetailDto create(User user, StrategyCreateDto strategyCreateDto);
}
