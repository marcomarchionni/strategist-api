package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;
import com.marcomarchionni.ibportfolio.repositories.FlexStatementRepository;
import com.marcomarchionni.ibportfolio.services.parsing.ResponseParser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UpdateServiceImpl implements UpdateService {

    private final ResponseParser parser;

    private final TradeService tradeService;

    private final DividendService dividendService;

    private final FlexStatementRepository flexStatementRepository;

    private final PositionService positionService;

    public UpdateServiceImpl(ResponseParser parser,
                             TradeService tradeService,
                             DividendService dividendService,
                             FlexStatementRepository flexStatementRepository,
                             PositionService positionService) {
        this.parser = parser;
        this.tradeService = tradeService;
        this.dividendService = dividendService;
        this.flexStatementRepository = flexStatementRepository;
        this.positionService = positionService;
    }

    @Override
    public void save(FlexQueryResponse dto) {

        List<Trade> trades = parser.getTrades(dto);
        List<Dividend> closedDividends = parser.getClosedDividends(dto);

        // update positions and dividends if flexQuery has the latest data
        if (hasTheLatestData(dto)) {
            List<Position> positions = parser.getPositions(dto);
            List<Dividend> openDividends = parser.getOpenDividends(dto);

            positionService.updatePositions(positions);
            dividendService.updateDividends(openDividends, closedDividends);

        } else {
            // only add missing closed dividends
            dividendService.saveOrIgnore(closedDividends);
        }

        // add missing trades
        tradeService.saveOrIgnore(trades);

        // Save flexStatement
        flexStatementRepository.save(parser.getFlexStatement(dto));
    }

    private boolean hasTheLatestData(FlexQueryResponse dto) {

        LocalDate dtoLastReportedDate = getLatestReportedDate(dto);
        LocalDate dbLastReportedDate = flexStatementRepository.findLastReportedDate();
        return dbLastReportedDate == null || dtoLastReportedDate.isAfter(dbLastReportedDate);
    }

    private LocalDate getLatestReportedDate(FlexQueryResponse dto) {
        return dto.getFlexStatements().getFlexStatement().getToDate();
    }
}
