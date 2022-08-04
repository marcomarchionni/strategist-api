package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.Portfolio;
import com.marcomarchionni.ibportfolio.model.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PortfolioListDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapperImpl implements PortfolioMapper{

    ModelMapper modelMapper;

    public PortfolioMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Portfolio toEntity(PortfolioCreateDto portfolioCreateDto) {
        return Portfolio.builder().name(portfolioCreateDto.getName()).build();
    }

    @Override
    public PortfolioListDto toPortfolioListDto(Portfolio portfolio) {
        return PortfolioListDto.builder().id(portfolio.getId()).name(portfolio.getName()).build();
    }

    @Override
    public PortfolioDetailDto toPortfolioDetailDto(Portfolio portfolio) {
        return modelMapper.map(portfolio, PortfolioDetailDto.class);
    }
}
