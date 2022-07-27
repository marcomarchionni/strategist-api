package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.TradeCriteriaDto;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TradeServiceImpl implements TradeService{

    private final TradeRepository tradeRepository;
    private final StrategyRepository strategyRepository;

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
    public List<Trade> findWithCriteria(TradeCriteriaDto c) {

        return tradeRepository.findWithParameters(
                c.getTradeDateFrom(), c.getTradeDateTo(), c.getTagged(), c.getSymbol(), c.getAssetCategory());
    }
}
