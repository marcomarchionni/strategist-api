package com.marcomarchionni.ibportfolio.dtos.update;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Trade;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CombinedUpdateReport {
    private final UpdateReport<FlexStatement> flexStatements;
    private final UpdateReport<Trade> trades;
    private final UpdateReport<Position> positions;
    private final UpdateReport<Dividend> dividends;
}
