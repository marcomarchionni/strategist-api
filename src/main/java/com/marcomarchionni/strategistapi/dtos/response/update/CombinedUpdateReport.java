package com.marcomarchionni.strategistapi.dtos.response.update;

import com.marcomarchionni.strategistapi.domain.FlexStatement;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummary;
import com.marcomarchionni.strategistapi.dtos.response.PositionSummary;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummary;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CombinedUpdateReport {
    private final UpdateReport<FlexStatement> flexStatements;
    private final UpdateReport<TradeSummary> trades;
    private final UpdateReport<PositionSummary> positions;
    private final UpdateReport<DividendSummary> dividends;
}
