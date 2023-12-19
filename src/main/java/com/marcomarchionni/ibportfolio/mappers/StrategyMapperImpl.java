package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategySummaryDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StrategyMapperImpl implements StrategyMapper {

    ModelMapper modelMapper;

    public StrategyMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Strategy toEntity(StrategyCreateDto strategyCreateDto) {
        Portfolio portfolio = Portfolio.builder().id(strategyCreateDto.getPortfolioId()).build();
        return Strategy.builder().name(strategyCreateDto.getName()).portfolio(portfolio).build();
    }

    @Override
    public StrategySummaryDto toStrategySummaryDto(Strategy strategy) {
        return modelMapper.map(strategy, StrategySummaryDto.class);
    }

    @Override
    public StrategyDetailDto toStrategyDetailDto(Strategy strategy) {
        return modelMapper.map(strategy, StrategyDetailDto.class);
    }
}
