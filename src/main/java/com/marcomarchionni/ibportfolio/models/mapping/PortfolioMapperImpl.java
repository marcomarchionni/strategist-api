package com.marcomarchionni.ibportfolio.models.mapping;

import com.marcomarchionni.ibportfolio.models.domain.Portfolio;
import com.marcomarchionni.ibportfolio.models.dtos.request.PortfolioCreateDto;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapperImpl implements PortfolioMapper{

    @Override
    public Portfolio toEntity(PortfolioCreateDto portfolioCreateDto) {
        Portfolio portfolio = new Portfolio();
        portfolio.setName(portfolioCreateDto.getName());
        return portfolio;
    }
}
