package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapperImpl implements PortfolioMapper {

    ModelMapper modelMapper;

    public PortfolioMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PortfolioSummary toPortfolioSummaryDto(Portfolio portfolio) {
        return modelMapper.map(portfolio, PortfolioSummary.class);
    }

    @Override
    public PortfolioDetail toPortfolioDetailDto(Portfolio portfolio) {
        return modelMapper.map(portfolio, PortfolioDetail.class);
    }
}
