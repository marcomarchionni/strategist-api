package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.models.FlexStatement;
import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.services.*;
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

    private final FlexStatementService flexStatementService;

    private final TradeService tradeService;

    private final PositionService positionService;

    private final DividendService dividendService;

    @Autowired
    public Updater(DataFetcher dataFetcher, ResponseParser responseParser, FlexStatementService FlexStatementService, TradeService tradeService, PositionService positionService, DividendService dividendService) {
        this.dataFetcher = dataFetcher;
        this.responseParser = responseParser;
        this.flexStatementService = FlexStatementService;
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
        List<TimeInterval> dataGaps = detectDataGaps();
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
        List<TimeInterval> dataGaps = detectDataGaps();
        if (dataGaps.size() > 0) {
            log.warn(">>> Data gaps detected: " + dataGaps);
        }
    }


    private void saveOrUpdate(FlexQueryResponseDto dto) {

        boolean dtoHasTheLatestData = checkIfDtoHasTheLatestData(dto);

        if (dtoHasTheLatestData) {
            log.info(">>> Dto has the latest data >>>>");

            // For new data only: delete old positions and save new positions,
            // delete old open dividends and save new open dividends in db
            log.info("Delete old positions from db and save new positions");
            positionService.deleteAllPositions();
            positionService.savePositions(responseParser.parsePositions(dto));

            log.info("Delete old open dividends, save new open dividends");
            dividendService.deleteOpenDividends();
            dividendService.saveDividends(responseParser.parseOpenDividends(dto));

        } else {
            log.info(">>> Dto contains archive data >>>>");
        }

        // For new and archive data: save flexInfo, save or update trades and closed dividends in db
        log.info("Save flexInfo");
        flexStatementService.save(responseParser.parseFlexStatement(dto));

        log.info("Save trades");
        tradeService.saveTrades(responseParser.parseTrades(dto));

        log.info("Save closed dividends");
        dividendService.saveDividends(responseParser.parseDividends(dto));

        log.info(">>> Dto data saved in db >>>");
    }

    private boolean checkIfDtoHasTheLatestData(FlexQueryResponseDto dto) {

        FlexStatement flexStatement = responseParser.parseFlexStatement(dto);
        LocalDate dtoLatestDateWithData = flexStatement.getToDate();

        LocalDate dbLatestDateWithData = flexStatementService.getLatestDateWithDataInDb();
        return dtoLatestDateWithData.isAfter(dbLatestDateWithData);
    }

    private List<TimeInterval> detectDataGaps() {

        List<TimeInterval> dataGaps = new ArrayList<>();
        List<FlexStatement> orderedFlexStatements = flexStatementService.findAllOrderedByFromDateAsc();

        if (orderedFlexStatements.size() <= 1) {

            // No data gaps with 1 or 0 updates, return empty list
            return dataGaps;

        } else {

            // search for possible data gaps
            LocalDate firstDayWithoutData = orderedFlexStatements.get(0).getToDate().plusDays(1);
            LocalDate fromDate, toDate, gapStart, gapEnd;

            for (int i = 1; i < orderedFlexStatements.size(); i++) {

                fromDate = orderedFlexStatements.get(i).getFromDate();
                toDate = orderedFlexStatements.get(i).getToDate();

                if (fromDate.isAfter(firstDayWithoutData)) {

                    // save data interval if >= 1 day
                    gapStart = firstDayWithoutData.plusDays(1);
                    gapEnd = fromDate.minusDays(1);
                    dataGaps.add(new TimeInterval(gapStart, gapEnd));
                }
                if (toDate.isAfter(firstDayWithoutData)) firstDayWithoutData = toDate;
            }
        }
        return dataGaps;
    }
}
