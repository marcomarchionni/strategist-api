package com.marcomarchionni.strategistapi.dtos.response;

import com.marcomarchionni.strategistapi.validators.PortfolioName;
import com.marcomarchionni.strategistapi.validators.StrategyName;
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
public class StrategyDetail {

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
    private List<TradeSummary> trades;

    @NotNull
    private List<PositionSummary> positions;

    @NotNull
    private List<DividendSummary> dividends;
}
