package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.Portfolio;
import com.marcomarchionni.ibportfolio.model.domain.Strategy;
import com.marcomarchionni.ibportfolio.model.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.StrategyListDto;
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
    public StrategyListDto toStrategyListDto(Strategy strategy) {
        return modelMapper.map(strategy, StrategyListDto.class);
    }

    @Override
    public StrategyDetailDto toStrategyDetailDto(Strategy strategy) {
        return modelMapper.map(strategy, StrategyDetailDto.class);
    }
}
