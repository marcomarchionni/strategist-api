package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.domain.Trade;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.FlexStatementRepository;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import com.marcomarchionni.ibportfolio.services.parsing.ResponseParser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UpdateServiceImpl implements UpdateService {

    private final ResponseParser parser;

    private final TradeRepository tradeRepository;

    private final PositionRepository positionRepository;

    private final DividendRepository dividendRepository;

    private final FlexStatementRepository flexStatementRepository;

    public UpdateServiceImpl(ResponseParser parser,
                             TradeRepository tradeRepository,
                             PositionRepository positionRepository,
                             DividendRepository dividendRepository,
                             FlexStatementRepository flexStatementRepository) {
        this.parser = parser;
        this.tradeRepository = tradeRepository;
        this.positionRepository = positionRepository;
        this.dividendRepository = dividendRepository;
        this.flexStatementRepository = flexStatementRepository;
    }

    @Override
    public void save(FlexQueryResponse dto) {

        // update positions and open dividends if flexQuery has the latest data
        if (hasTheLatestData(dto)) {
            List<Position> positions = parser.getPositions(dto);
            List<Dividend> openDividends = parser.getOpenDividends(dto);
            positionRepository.saveAll(positions);
            dividendRepository.saveAll(openDividends);
        }

        // update trades and closed dividends
        List<Trade> trades = parser.getTrades(dto);
        List<Dividend> closedDividends = parser.getClosedDividends(dto);
        tradeRepository.saveAll(trades);
        dividendRepository.saveAll(closedDividends);

        // Save flexStatement
        flexStatementRepository.save(parser.getFlexStatement(dto));
    }

    private boolean hasTheLatestData(FlexQueryResponse dto) {

        LocalDate dtoLastReportedDate = dto.getFlexStatements().getFlexStatement().getToDate();
        LocalDate dbLastReportedDate = flexStatementRepository.findLastReportedDate();
        return dbLastReportedDate == null || dtoLastReportedDate.isAfter(dbLastReportedDate);
    }
}
