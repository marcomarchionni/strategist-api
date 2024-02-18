package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetail;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;

public interface StrategyMapper {

    Strategy toEntity(StrategyCreate strategyCreate);

    StrategySummary toStrategySummaryDto(Strategy strategy);

    StrategyDetail toStrategyDetailDto(Strategy strategy);
}
