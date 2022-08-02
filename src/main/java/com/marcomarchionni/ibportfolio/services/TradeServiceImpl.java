package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.models.domain.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.TradeListDto;
import com.marcomarchionni.ibportfolio.models.mapping.TradeMapper;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final StrategyRepository strategyRepository;
    private final TradeMapper tradeMapper;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository, StrategyRepository strategyRepository, TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.strategyRepository = strategyRepository;
        this.tradeMapper = tradeMapper;
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
    public List<TradeListDto> findByParams(TradeFindDto tradeFind) {
        List<Trade> trades = tradeRepository.findWithParameters(
                tradeFind.getTradeDateFrom(),
                tradeFind.getTradeDateTo(),
                tradeFind.getTagged(),
                tradeFind.getSymbol(),
                tradeFind.getAssetCategory());
        return trades.stream().map(tradeMapper::toTradeListDto).collect(Collectors.toList());
    }
}
