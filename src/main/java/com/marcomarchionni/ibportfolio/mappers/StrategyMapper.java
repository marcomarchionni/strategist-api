package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategySummaryDto;

public interface StrategyMapper {

    Strategy toEntity(StrategyCreateDto strategyCreateDto);

    StrategySummaryDto toStrategyListDto(Strategy strategy);

    StrategyDetailDto toStrategyDetailDto(Strategy strategy);
}
