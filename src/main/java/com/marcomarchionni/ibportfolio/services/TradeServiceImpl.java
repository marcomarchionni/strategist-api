package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import com.marcomarchionni.ibportfolio.rest.exceptionhandling.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public boolean saveAll(List<Trade> trades) {

        try {
            tradeRepository.saveAll(trades);
            return true;
        } catch (Exception e) {
            log.error("Exception of some kind: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public List<Trade> findAll() {

        return tradeRepository.findAll();
    }

    @Override
    public Trade updateStrategyId(Trade trade) {

        Trade tradeToUpdate = tradeRepository.findById(trade.getId()).orElseThrow(
                ()-> new EntityNotFoundException("Trade with id: " + trade.getId() + " not found")
        );

        Strategy strategyToAssign = strategyRepository.findById(trade.getStrategyId()).orElseThrow(
                ()-> new EntityNotFoundException("Strategy with id: " + trade.getStrategyId() + " not found")
        );

        tradeToUpdate.setStrategyId(strategyToAssign.getId());
        return tradeRepository.save(tradeToUpdate);
    }

    @Override
    public boolean tradeDoesNotExist(Long id) {
        return tradeRepository.findById(id).isEmpty();
    }

    @Override
    public List<Trade> findWithParameters(LocalDate startDate, LocalDate endDate, Boolean tagged, String symbol, String assetCategory) {

        if (symbol != null && Objects.equals(symbol, "")) {
            symbol = null;
        }
        if (assetCategory != null && Objects.equals(assetCategory, "")) {
            assetCategory = null;
        }
        if (startDate != null && startDate.isBefore(MIN_DATE)) {
            startDate = MIN_DATE;
        }
        if (endDate != null && endDate.isAfter(MAX_DATE)) {
            endDate = MAX_DATE;
        }
        return tradeRepository.findWithParameters(startDate, endDate, tagged, symbol, assetCategory);
    }
}
