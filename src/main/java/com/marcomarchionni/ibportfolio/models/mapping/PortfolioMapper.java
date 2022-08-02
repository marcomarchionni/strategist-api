package com.marcomarchionni.ibportfolio.models.mapping;

import com.marcomarchionni.ibportfolio.models.domain.Portfolio;
import com.marcomarchionni.ibportfolio.models.dtos.request.PortfolioCreateDto;

public interface PortfolioMapper {

    Portfolio toEntity(PortfolioCreateDto portfolioCreateDto);
}
