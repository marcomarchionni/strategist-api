package com.marcomarchionni.ibportfolio.model.dtos.response;

import com.marcomarchionni.ibportfolio.model.validation.PortfolioName;
import com.marcomarchionni.ibportfolio.model.validation.StrategyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyListDto {

    @NotNull
    private Long id;

    @StrategyName
    private String name;

    @NotNull
    private Long portfolioId;

    @PortfolioName
    private String portfolioName;
}
