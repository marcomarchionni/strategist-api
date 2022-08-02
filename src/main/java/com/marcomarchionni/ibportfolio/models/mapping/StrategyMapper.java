package com.marcomarchionni.ibportfolio.models.mapping;

import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyListDto;

public interface StrategyMapper {

    Strategy toEntity(StrategyCreateDto strategyCreateDto);

    StrategyListDto toStrategyListDto(Strategy strategy);

    StrategyDetailDto toStrategyDetailDto(Strategy strategy);
}
