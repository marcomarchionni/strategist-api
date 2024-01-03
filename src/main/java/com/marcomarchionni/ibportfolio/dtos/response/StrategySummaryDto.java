package com.marcomarchionni.ibportfolio.dtos.response;

import com.marcomarchionni.ibportfolio.dtos.validators.PortfolioName;
import com.marcomarchionni.ibportfolio.dtos.validators.StrategyName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategySummaryDto {

    @NotNull
    private Long id;

    @StrategyName
    private String name;

    @NotNull
    private Long portfolioId;

    @NotNull
    private String accountId;

    @PortfolioName
    private String portfolioName;
}
