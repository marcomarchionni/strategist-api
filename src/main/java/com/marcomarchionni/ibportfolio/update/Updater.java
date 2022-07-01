package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.models.FlexStatement;
import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class Updater {

    private final DataFetcher dataFetcher;

    private final ResponseParser responseParser;

    private final FlexStatementService flexStatementService;

    private final PositionService positionService;

    private final TradeService tradeService;

    private final DividendService dividendService;

    @Autowired
    public Updater(DataFetcher dataFetcher, ResponseParser responseParser, FlexStatementService flexStatementService, PositionService positionService, TradeService tradeService, DividendService dividendService) {
        this.dataFetcher = dataFetcher;
        this.responseParser = responseParser;
        this.flexStatementService = flexStatementService;
        this.positionService = positionService;
        this.tradeService = tradeService;
        this.dividendService = dividendService;
    }

    @Transactional
    public void updateFromServer() {

        FlexQueryResponseDto flexQueryResponseDto = dataFetcher.fetchFromServer();

        if (flexQueryResponseDto == null) {
            log.info("Exception: Invalid response from server...");
            return;
        }

        // usiamo il servizio di parsing per ottenere le entity da aggiungere al db
        log.info("Data retrieved, dispatching to parser");
        FlexQueryData flexQueryData = responseParser.parse(flexQueryResponseDto);

        // utilizziamo il servizio che ha il compito di gestire la persistenza per salvare i nostri dati sul db

        log.info("Data parsed, dispatching to persistence layer");
        persistFlexQueryData(flexQueryData);

    }

    @Transactional
    public void updateFromFile() {

        FlexQueryResponseDto flexQueryResponseDto = dataFetcher.fetchFromFile();

        if (flexQueryResponseDto == null) {
            log.info("Exception: Invalid file...");
            return;
        }

        // usiamo il servizio di parsing per ottenere le entity da aggiungere al db
        log.info("Data retrieved, dispatching to parser");
        FlexQueryData flexQueryData = responseParser.parse(flexQueryResponseDto);

        // utilizziamo il servizio che ha il compito di gestire la persistenza per salvare i nostri dati sul db
        log.info("Data parsed, dispatching to persistence layer");
        persistFlexQueryData(flexQueryData);

    }

    private void persistFlexQueryData(FlexQueryData flexQueryData) {
        // utilizziamo il servizio che ha il compito di gestire la persistenza per salvare i nostri dati sul db

        // get last reportDate from db
        LocalDate lastReportDateInDb = flexStatementService.getLastReportDate();
        LocalDate flexReportDate = flexQueryData.getFlexStatement().getToDate();

        boolean flexIsNew = flexReportDate.isAfter(lastReportDateInDb);

        // save flexStatement
        flexStatementService.saveFlexStatement(flexQueryData.getFlexStatement());

        if (flexIsNew) {
            log.info(">>> Flex is new >>>>");
            // delete old positions from db and add new positions
            boolean oldPositionsDeleted = positionService.deleteAllPositions();

            if (!oldPositionsDeleted) {
                log.error("Could not delete old positions on the DB");
            } else {
                log.info("Old positions deleted...");
            }
            boolean positionSaved = positionService.savePositions(flexQueryData.getPositions());
            if (!positionSaved) {
                log.error("Could not store all the positions on the DB");
            } else {
                log.info("New positions saved...");
            }
        } else {
            log.info(">>> Flex is old >>>>");
        }

        //save trades
        boolean tradeSaved = tradeService.saveTrades(flexQueryData.getTrades());
        if (!tradeSaved) {
            log.error("Could not store all the trades on the DB");
        } else {
            log.info("New trades saved...");
        }

        if (flexIsNew) {
            // delete old open dividends
            boolean deletedOpenDividends = dividendService.deleteOpenDividends();
            if (!deletedOpenDividends) {
                log.error("Could not delete open dividends from the DB");
            } else {
                log.info("Old open dividends deleted...");
            }
            // save new dividends and open dividends
            boolean dividendSaved = dividendService.saveDividends(flexQueryData.getDividends());
            boolean openDividendSaved = dividendService.saveDividends(flexQueryData.getOpenDividends());
            if (!dividendSaved || !openDividendSaved) {
                log.error("Could not store all the dividends on the DB");
            } else {
                log.info("New dividends and open dividends saved...");
            }
        } else {
            // save dividends only
            boolean dividendSaved = dividendService.saveDividends(flexQueryData.getDividends());
            if (!dividendSaved) {
                log.error("Could not store all the dividends on the DB");
            } else {
                log.info("New dividends saved...");
            }
        }

        log.info("End update. Daily alignment completed successfully!");
        log.info("End update");
    }

    public List<Gap> detectGaps() {

        List<Gap> gaps = new ArrayList<>();

        List<FlexStatement> orderedFlexStatements = flexStatementService.getAllOrderedByFromDateAsc();

        if (orderedFlexStatements.size() > 1) {
            LocalDate newStart;
            LocalDate newEnd;
            LocalDate prevEnd = orderedFlexStatements.get(0).getToDate().plusDays(1);

            for (int i = 1; i < orderedFlexStatements.size(); i++) {

                newStart = orderedFlexStatements.get(i).getFromDate();
                newEnd = orderedFlexStatements.get(i).getToDate();

                if (newStart.isAfter(prevEnd)) {
                    gaps.add(new Gap(prevEnd, newStart));
                }
                if (newEnd.isAfter(prevEnd)) prevEnd = newEnd;
            }
        }
        return gaps;
    }
}
