package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.*;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.services.parsers.ResponseParser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UpdateServiceImpl implements UpdateService {

    private final ResponseParser parser;

    private final TradeService tradeService;

    private final DividendService dividendService;

    private final FlexStatementService flexStatementService;

    private final PositionService positionService;

    public UpdateServiceImpl(ResponseParser parser,
                             TradeService tradeService,
                             DividendService dividendService,
                             FlexStatementService flexStatementService, PositionService positionService) {
        this.parser = parser;
        this.tradeService = tradeService;
        this.dividendService = dividendService;
        this.flexStatementService = flexStatementService;
        this.positionService = positionService;
    }

    @Override
    public CombinedUpdateReport save(User user, FlexQueryResponseDto dto) {
        //Declare report variables
        UpdateReport<Position> positionReport;
        UpdateReport<Dividend> dividendReport;

        // Extract trades and dividends from dto
        List<Trade> trades = parser.getTrades(dto);
        List<Dividend> closedDividends = parser.getClosedDividends(dto);

        // Check if dto has the latest data
        LocalDate toDateInDto = parser.getFlexStatementToDate(dto);
        LocalDate latestToDateInDb = flexStatementService.findLatestToDate(user);
        boolean dtoHasTheLatestData = toDateInDto.isAfter(latestToDateInDb);

        if (dtoHasTheLatestData) {
            // extract positions and open dividends from dto
            List<Position> positions = parser.getPositions(dto);
            List<Dividend> openDividends = parser.getOpenDividends(dto);

            // Update positions, open dividends, closed dividends
            positionReport = positionService.updatePositions(positions);
            dividendReport = dividendService.updateDividends(user, openDividends, closedDividends);

        } else {
            // Add new closed dividends, no action on positions
            positionReport = UpdateReport.<Position>builder().build();
            dividendReport = dividendService.addOrSkip(user, closedDividends);
        }

        // Add new or missing trades
        UpdateReport<Trade> tradeReport = tradeService.addOrSkip(trades);

        // Save flexStatement
        UpdateReport<FlexStatement> flexStatementReport = flexStatementService.save(user, parser.getFlexStatement(dto));

        return CombinedUpdateReport.builder().flexStatements(flexStatementReport).trades(tradeReport)
                .positions(positionReport).dividends(dividendReport).build();
    }
}
