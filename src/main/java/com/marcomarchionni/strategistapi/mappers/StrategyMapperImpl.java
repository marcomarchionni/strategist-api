package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetail;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StrategyMapperImpl implements StrategyMapper {

    ModelMapper modelMapper;

    public StrategyMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Strategy toEntity(StrategyCreate strategyCreate) {
        Portfolio portfolio = Portfolio.builder().id(strategyCreate.getPortfolioId()).build();
        return Strategy.builder().name(strategyCreate.getName()).portfolio(portfolio).build();
    }

    @Override
    public StrategySummary toStrategySummaryDto(Strategy strategy) {
        return modelMapper.map(strategy, StrategySummary.class);
    }

    @Override
    public StrategyDetail toStrategyDetailDto(Strategy strategy) {
        return modelMapper.map(strategy, StrategyDetail.class);
    }
}
