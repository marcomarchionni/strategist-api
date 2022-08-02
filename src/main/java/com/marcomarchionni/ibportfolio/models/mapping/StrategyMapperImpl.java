package com.marcomarchionni.ibportfolio.models.mapping;

import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyListDto;
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
        Strategy strategy = modelMapper.map(strategyCreateDto, Strategy.class);
        return strategy;
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
