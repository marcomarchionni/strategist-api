package com.marcomarchionni.ibportfolio.models.dtos.response;

import com.marcomarchionni.ibportfolio.models.domain.Dividend;
import com.marcomarchionni.ibportfolio.models.domain.Position;
import com.marcomarchionni.ibportfolio.models.validation.PortfolioName;
import com.marcomarchionni.ibportfolio.models.validation.StrategyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
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
    private List<TradeListDto> trades;

    @NotNull
    private List<Position> positions;

    @NotNull
    private List<Dividend> dividends;
}
