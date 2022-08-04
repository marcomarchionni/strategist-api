package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.Strategy;
import com.marcomarchionni.ibportfolio.model.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.StrategyListDto;

public interface StrategyMapper {

    Strategy toEntity(StrategyCreateDto strategyCreateDto);

    StrategyListDto toStrategyListDto(Strategy strategy);

    StrategyDetailDto toStrategyDetailDto(Strategy strategy);
}
