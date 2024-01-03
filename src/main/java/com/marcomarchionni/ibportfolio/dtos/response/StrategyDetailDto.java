package com.marcomarchionni.ibportfolio.dtos.response;

import com.marcomarchionni.ibportfolio.dtos.validators.PortfolioName;
import com.marcomarchionni.ibportfolio.dtos.validators.StrategyName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyDetailDto {

    @NotNull
    private Long id;

    @StrategyName
    private String name;

    @NotNull
    private Long portfolioId;

    @PortfolioName
    private String portfolioName;

    @NotNull
    private String accountId;

    @NotNull
    private List<TradeSummaryDto> trades;

    @NotNull
    private List<PositionSummaryDto> positions;

    @NotNull
    private List<DividendSummaryDto> dividends;
}
