package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions.UnableToProcessQueryException;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.exceptions.UnableToSaveEntitiesException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TradeServiceImpl implements TradeService{

    private final TradeRepository tradeRepository;
    private final StrategyRepository strategyRepository;

    private final LocalDate MIN_DATE = LocalDate.of(1000, 1, 1);
    private final LocalDate MAX_DATE = LocalDate.of(9999, 12, 31);

    public TradeServiceImpl(TradeRepository tradeRepository, StrategyRepository strategyRepository) {
        this.tradeRepository = tradeRepository;
        this.strategyRepository = strategyRepository;
    }

    @Override
    public void saveAll(List<Trade> trades) {
        try {
            tradeRepository.saveAll(trades);
        }
        catch (Exception exc) {
            throw new UnableToSaveEntitiesException(exc.getMessage());
        }
    }

    @Override
    public Trade updateStrategyId(Trade trade) {

        if ( trade.getId() == null ||  tradeRepository.findById(trade.getId()).isEmpty()) {
            throw new EntityNotFoundException("Trade with id: " + trade.getId() + " not found");
        }
        Trade tradeToUpdate = tradeRepository.findById(trade.getId()).get();

        if ( trade.getStrategyId() == null || strategyRepository.findById(trade.getStrategyId()).isEmpty() ) {
            throw new EntityNotFoundException("Strategy with id: " + trade.getStrategyId() + " not found");
        }
        Strategy strategyToAssign = strategyRepository.findById(trade.getStrategyId()).get();

        tradeToUpdate.setStrategyId(strategyToAssign.getId());
        return tradeRepository.save(tradeToUpdate);
    }

    @Override
    public List<Trade> findWithParameters(LocalDate startDate, LocalDate endDate, Boolean tagged, String symbol, String assetCategory) {

        if (!StringUtils.hasText(symbol)) {
            symbol = null;
        }
        if (!StringUtils.hasText(assetCategory)) {
            assetCategory = null;
        }
        if (startDate != null && startDate.isBefore(MIN_DATE)) {
            startDate = null;
        }
        if (endDate != null && endDate.isAfter(MAX_DATE)) {
            endDate = null;
        }
        try {
            return tradeRepository.findWithParameters(startDate, endDate, tagged, symbol, assetCategory);
        }
        catch (Exception exception) {
            throw new UnableToProcessQueryException(exception.getMessage());
        }
    }
}
