package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.model.domain.FlexInfo;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.services.DividendService;
import com.marcomarchionni.ibportfolio.services.FlexInfoService;
import com.marcomarchionni.ibportfolio.services.PositionService;
import com.marcomarchionni.ibportfolio.services.TradeService;
import com.marcomarchionni.ibportfolio.update.parsing.ResponseParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Transactional
public class Updater {

    private final DataFetcher dataFetcher;

    private final ResponseParser responseParser;

    private final FlexInfoService flexInfoService;

    private final TradeService tradeService;

    private final PositionService positionService;

    private final DividendService dividendService;

    @Autowired
    public Updater(DataFetcher dataFetcher, ResponseParser responseParser, FlexInfoService flexInfoService, TradeService tradeService, PositionService positionService, DividendService dividendService) {
        this.dataFetcher = dataFetcher;
        this.responseParser = responseParser;
        this.flexInfoService = flexInfoService;
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
        if (dataGaps.size() > 0) {
            log.warn(">>> Data gaps detected: " + dataGaps);
        }
    }


    public void updateFromFile(File xmlFile) throws Exception {

        // preleviamo i dati dal file
        FlexQueryResponseDto dto = dataFetcher.fetchFromFile(xmlFile);
        if (dto == null) {
            log.info("Exception: Invalid file...");
            return;
        }

        // salviamo o aggiorniamo i dati sul db
        saveOrUpdate(dto);

        // segnaliamo eventuali intervalli temporali senza dati nel db
        List<TimeInterval> dataGaps = detectUndocumentedTimeIntervals();
        if (dataGaps.size() > 0) {
            log.warn(">>> Data gaps detected: " + dataGaps);
        }
    }


    private void saveOrUpdate(FlexQueryResponseDto dto) {

        // For new data only:
        // delete old positions and save new positions,
        // delete old open dividends and save new open dividends
        if (hasTheLatestData(dto)) {
            positionService.deleteAll();
            dividendService.deleteAllOpenDividends();
            positionService.saveAll(responseParser.parsePositions(dto));
            dividendService.saveDividends(responseParser.parseOpenDividends(dto));
        }

        // For new and archive data:
        // save or update trades and closed dividends in db
        tradeService.saveAll(responseParser.parseTrades(dto));
        dividendService.saveDividends(responseParser.parseClosedDividends(dto));

        // Save updateInfo
        flexInfoService.save(responseParser.parseFlexInfo(dto));
    }

    private boolean hasTheLatestData(FlexQueryResponseDto dto) {

        LocalDate dtoLastReportDate = responseParser.parseFlexInfo(dto).getToDate();
        LocalDate dbLastReportDate = flexInfoService.getLastReportDate();
        return dtoLastReportDate.isAfter(dbLastReportDate);
    }

    // Detects if the sequence of updates has left undocumented time intervals
    private List<TimeInterval> detectUndocumentedTimeIntervals() {

        List<TimeInterval> undocumentedTimeIntervals = new ArrayList<>();
        List<FlexInfo> orderedFlexInfos = flexInfoService.findAllOrderedByFromDateAsc();

        if (orderedFlexInfos.size() <= 1) {

            // No possible gap in documentation with 1 or 0 updates
            return undocumentedTimeIntervals;

        } else {

            LocalDate firstDayWithoutData = orderedFlexInfos.get(0).getToDate().plusDays(1);
            LocalDate fromDate, toDate, lastDayWithoutData;

            for (int i = 1; i < orderedFlexInfos.size(); i++) {

                fromDate = orderedFlexInfos.get(i).getFromDate();
                toDate = orderedFlexInfos.get(i).getToDate();

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
