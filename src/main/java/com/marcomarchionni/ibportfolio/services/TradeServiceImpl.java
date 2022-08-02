package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.TradeFindDto;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TradeServiceImpl implements TradeService{

    private final TradeRepository tradeRepository;
    private final StrategyRepository strategyRepository;

    @Autowired
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
    public Trade updateStrategyId(UpdateStrategyDto tradeUpdate) {

        Trade trade = tradeRepository.findById(tradeUpdate.getId()).orElseThrow(
                ()-> new EntityNotFoundException("Trade with id: " + tradeUpdate.getId() + " not found")
        );
        Strategy strategyToAssign = strategyRepository.findById(tradeUpdate.getStrategyId()).orElseThrow(
                ()-> new EntityNotFoundException("Strategy with id: " + tradeUpdate.getStrategyId() + " not found")
        );
        trade.setStrategy(strategyToAssign);
        return tradeRepository.save(trade);
    }

    @Override
    public List<Trade> findByParams(TradeFindDto c) {
        return tradeRepository.findWithParameters(
                c.getTradeDateFrom(), c.getTradeDateTo(), c.getTagged(), c.getSymbol(), c.getAssetCategory());
    }
}
