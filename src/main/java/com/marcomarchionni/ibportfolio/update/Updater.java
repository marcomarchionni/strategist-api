package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.services.DividendService;
import com.marcomarchionni.ibportfolio.services.PositionService;
import com.marcomarchionni.ibportfolio.services.ResponseParser;
import com.marcomarchionni.ibportfolio.services.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class Updater {

    private final DataFetcher dataFetcher;

    private final ResponseParser responseParser;

    private final PositionService positionService;

    private final TradeService tradeService;

    private final DividendService dividendService;

    @Autowired
    public Updater(DataFetcher dataFetcher, ResponseParser responseParser, PositionService positionService, TradeService tradeService, DividendService dividendService) {
        this.dataFetcher = dataFetcher;
        this.responseParser = responseParser;
        this.positionService = positionService;
        this.tradeService = tradeService;
        this.dividendService = dividendService;
    }

    @Transactional
    public void updateFromServer() {

        FlexQueryResponseDto flexQueryResponseDto = dataFetcher.fetchFromServer();

        if ( flexQueryResponseDto == null) {
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

        if ( flexQueryResponseDto == null) {
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

    private void persistFlexQueryData (FlexQueryData flexQueryData) {
        // utilizziamo il servizio che ha il compito di gestire la persistenza per salvare i nostri dati sul db

        log.info("Dispatching data to persistence layer");

        boolean positionSaved = positionService.savePositions(flexQueryData.getPositions());
        if (!positionSaved) {
            log.error("Could not store all the positions on the DB");
        }

        boolean tradeSaved = tradeService.saveTrades(flexQueryData.getTrades());
        if (!tradeSaved) {
            log.error("Could not store all the trades on the DB");
        }

        boolean dividendSaved = dividendService.saveDividends(flexQueryData.getDividends());
        if (!dividendSaved) {
            log.error("Could not store all the dividends on the DB");
        }

        log.info("End update. Daily alignment completed successfully!");
        log.info("End update");
    }
}
