package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreateDto;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetailDto;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummaryDto;

public interface StrategyMapper {

    Strategy toEntity(StrategyCreateDto strategyCreateDto);

    StrategySummaryDto toStrategySummaryDto(Strategy strategy);

    StrategyDetailDto toStrategyDetailDto(Strategy strategy);
}
