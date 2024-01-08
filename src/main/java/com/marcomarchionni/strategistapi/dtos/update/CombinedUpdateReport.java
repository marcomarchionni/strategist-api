package com.marcomarchionni.strategistapi.dtos.update;

import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.domain.FlexStatement;
import com.marcomarchionni.strategistapi.domain.Position;
import com.marcomarchionni.strategistapi.domain.Trade;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CombinedUpdateReport {
    private final UpdateReport<FlexStatement> flexStatements;
    private final UpdateReport<Trade> trades;
    private final UpdateReport<Position> positions;
    private final UpdateReport<Dividend> dividends;
}
