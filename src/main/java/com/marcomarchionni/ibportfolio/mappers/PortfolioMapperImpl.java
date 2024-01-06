package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioSummaryDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapperImpl implements PortfolioMapper {

    ModelMapper modelMapper;

    public PortfolioMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PortfolioSummaryDto toPortfolioSummaryDto(Portfolio portfolio) {
        return modelMapper.map(portfolio, PortfolioSummaryDto.class);
    }

    @Override
    public PortfolioDetailDto toPortfolioDetailDto(Portfolio portfolio) {
        return modelMapper.map(portfolio, PortfolioDetailDto.class);
    }
}
