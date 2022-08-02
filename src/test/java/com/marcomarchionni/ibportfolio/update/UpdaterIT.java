package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.models.domain.Dividend;
import com.marcomarchionni.ibportfolio.models.domain.FlexInfo;
import com.marcomarchionni.ibportfolio.models.domain.Position;
import com.marcomarchionni.ibportfolio.models.domain.Trade;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.FlexInfoRepository;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import com.marcomarchionni.ibportfolio.services.ResponseParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.jdbc.Sql;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UpdaterIT {

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    DividendRepository dividendRepository;

    @Autowired
    FlexInfoRepository flexInfoRepository;

    @Value("classpath:/flex/LastMonth.xml")
    Resource lastMonthResource;

    @Value("classpath:/flex/Last30Days.xml")
    Resource last30DaysResource;

    @Autowired
    ResponseParser responseParser;

    @Autowired
    Updater updater;

    @Test
    @Sql("/initIbTestDb.sql")
    public void updateFromFileTestEmptyDb() throws Exception {

        File flexQuery = last30DaysResource.getFile();
        assertNotNull(flexQuery);

        updater.updateFromFile(flexQuery);

        List<Trade> trades = tradeRepository.findAll();
        List<Position> positions = positionRepository.findAll();
        List<Dividend> dividends = dividendRepository.findAll();
        List<FlexInfo> flexInfos = flexInfoRepository.findAll();

        assertTrue(trades.size() > 0);
        assertTrue(positions.size() > 0);
        assertTrue(dividends.size() > 0);
        assertTrue(flexInfos.size() > 0);
        assertEquals(8, trades.size());
        assertEquals(94, positions.size());
        assertEquals(6, dividends.size());
        assertEquals(1, flexInfos.size());
    }

    @Test
    @Sql("/initIbTestDb.sql")
    public void updateFromFileTestEmptyDb2() throws Exception {

        File flexQuery = lastMonthResource.getFile();

        assertTrue(flexQuery.isFile());

        updater.updateFromFile(flexQuery);

        List<Trade> trades = tradeRepository.findAll();
        List<Position> positions = positionRepository.findAll();
        List<Dividend> dividends = dividendRepository.findAll();
        List<FlexInfo> flexInfos = flexInfoRepository.findAll();

        assertTrue(trades.size() > 0);
        assertTrue(positions.size() > 0);
        assertTrue(dividends.size() > 0);
        assertTrue(flexInfos.size() > 0);
        assertEquals(1, flexInfos.size());
        assertEquals(10, trades.size());
        assertEquals(94, positions.size());
        assertEquals(6, dividends.size());
    }

    @Test
    @Sql("/initIbTestDb.sql")
    public void updateFromFileTest2Files() throws Exception {

        File lastMonth = lastMonthResource.getFile();
        File last30Days = last30DaysResource.getFile();

        assertTrue(lastMonth.isFile());
        assertTrue(last30Days.isFile());

        updater.updateFromFile(lastMonth);

        List<Trade> trades = tradeRepository.findAll();
        List<Position> positions = positionRepository.findAll();
        List<Dividend> dividends = dividendRepository.findAll();
        List<FlexInfo> flexInfos = flexInfoRepository.findAll();

        assertTrue(trades.size() > 0);
        assertTrue(positions.size() > 0);
        assertTrue(dividends.size() > 0);
        assertTrue(flexInfos.size() > 0);
        assertEquals(1, flexInfos.size());
        assertEquals(10, trades.size());
        assertEquals(94, positions.size());
        assertEquals(6, dividends.size());

        updater.updateFromFile(last30Days);

        trades = tradeRepository.findAll();
        positions = positionRepository.findAll();
        dividends = dividendRepository.findAll();
        flexInfos = flexInfoRepository.findAll();
        assertTrue(trades.size() > 0);
        assertTrue(positions.size() > 0);
        assertTrue(dividends.size() > 0);
        assertTrue(flexInfos.size() > 0);
        assertEquals(2, flexInfos.size());
        assertEquals(14, trades.size());
        assertEquals(94, positions.size());
        assertEquals(7, dividends.size());
    }
}
