package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.services.DividendService;
import com.marcomarchionni.ibportfolio.services.FlexStatementService;
import com.marcomarchionni.ibportfolio.services.PositionService;
import com.marcomarchionni.ibportfolio.services.TradeService;
import com.marcomarchionni.ibportfolio.services.parsing.OldResponseParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Transactional
public class Updater {

    private final DataFetcher dataFetcher;

    private final OldResponseParser oldResponseParser;

    private final FlexStatementService flexStatementService;

    private final TradeService tradeService;

    private final PositionService positionService;

    private final DividendService dividendService;

    @Autowired
    public Updater(DataFetcher dataFetcher, OldResponseParser oldResponseParser, FlexStatementService flexStatementService, TradeService tradeService, PositionService positionService, DividendService dividendService) {
        this.dataFetcher = dataFetcher;
        this.oldResponseParser = oldResponseParser;
        this.flexStatementService = flexStatementService;
        this.tradeService = tradeService;
        this.positionService = positionService;
        this.dividendService = dividendService;
    }

    public void updateFromServer() {

        // get data from server
        FlexQueryResponseDto dto = dataFetcher.fetchFromServer();
        if (dto == null) {
            log.warn("Exception: Invalid response from server...");
            return;
        }

        // save or update data on db
        saveOrUpdate(dto);

        // warn if there are time intervals without data in db
        List<TimeInterval> dataGaps = detectUndocumentedTimeIntervals();
        if (!dataGaps.isEmpty()) {
            log.warn(">>> Data gaps detected: " + dataGaps);
        }
    }

    private void saveOrUpdate(FlexQueryResponseDto dto) {

        // For new data only:
        // delete old positions and save new positions,
        // delete old open dividends and save new open dividends
        if (hasTheLatestData(dto)) {
            dividendService.deleteAllOpenDividends();
            positionService.updatePositions(oldResponseParser.parsePositions(dto));
            dividendService.saveDividends(oldResponseParser.parseOpenDividends(dto));
        }

        // For new and archive data:
        // save or update trades and closed dividends in db
        tradeService.saveAll(oldResponseParser.parseTrades(dto));
        dividendService.saveDividends(oldResponseParser.parseClosedDividends(dto));

        // Save updateInfo
        flexStatementService.save(oldResponseParser.parseFlexStatement(dto));
    }

    private boolean hasTheLatestData(FlexQueryResponseDto dto) {

        LocalDate dtoLastReportDate = oldResponseParser.parseFlexStatement(dto).getToDate();
        LocalDate dbLastReportDate = flexStatementService.getLastReportDate();
        return dtoLastReportDate.isAfter(dbLastReportDate);
    }

    // Detects if the sequence of updates has left undocumented time intervals
    private List<TimeInterval> detectUndocumentedTimeIntervals() {

        List<TimeInterval> undocumentedTimeIntervals = new ArrayList<>();
        List<FlexStatement> orderedFlexStatements = flexStatementService.findAllOrderedByFromDateAsc();

        if (orderedFlexStatements.size() <= 1) {

            // No possible gap in documentation with 1 or 0 updates
            return undocumentedTimeIntervals;

        } else {

            LocalDate firstDayWithoutData = orderedFlexStatements.get(0).getToDate().plusDays(1);
            LocalDate fromDate, toDate, lastDayWithoutData;

            for (int i = 1; i < orderedFlexStatements.size(); i++) {

                fromDate = orderedFlexStatements.get(i).getFromDate();
                toDate = orderedFlexStatements.get(i).getToDate();

                if (fromDate.isAfter(firstDayWithoutData)) {

                    // save data interval if >= 1 day
                    lastDayWithoutData = fromDate.minusDays(1);
                    undocumentedTimeIntervals.add(new TimeInterval(firstDayWithoutData, lastDayWithoutData));
                }
                if (toDate.isAfter(firstDayWithoutData)) firstDayWithoutData = toDate.plusDays(1);
            }
        }
        return undocumentedTimeIntervals;
    }
}
