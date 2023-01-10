package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.FlexStatementRepository;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FileUpdaterIT {

    @Autowired
    PositionRepository positionRepository;
    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    DividendRepository dividendRepository;
    @Autowired
    FlexStatementRepository flexStatementRepository;

    @Value("/flex/SimpleJune2022.xml")
    File flexQuery;

    @Autowired
    FileUpdater fileUpdater;

    @Test
    void updateFromFileEmptyDbTest() throws IOException {

        clearDb();

        fileUpdater.update(flexQuery);

        List<Trade> trades = tradeRepository.findAll();
        List<Position> positions = positionRepository.findAll();
        List<Dividend> dividends = dividendRepository.findAll();
        List<FlexStatement> flexStatements = flexStatementRepository.findAll();

        assertTrue(trades.size() > 0);
        assertTrue(positions.size() > 0);
        assertTrue(dividends.size() > 0);
        assertTrue(flexStatements.size() > 0);
        Optional<Dividend> result = dividends.stream().filter(d -> d.getOpenClosed() == null).findFirst();
        assertTrue(result.isEmpty());
    }

    //TODO: test position dividend and trade update

    private void clearDb() {
        positionRepository.deleteAll();
        tradeRepository.deleteAll();
        dividendRepository.deleteAll();
        flexStatementRepository.deleteAll();
    }
}
