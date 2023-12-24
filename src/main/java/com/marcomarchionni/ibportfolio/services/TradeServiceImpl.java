package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.TradeSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.TradeMapper;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
    public TradeSummaryDto updateStrategyId(User user, UpdateStrategyDto tradeUpdate) {

        Trade trade = tradeRepository.findById(tradeUpdate.getId()).orElseThrow(
                () -> new EntityNotFoundException("Trade with id: " + tradeUpdate.getId() + " not found")
        );
        Strategy strategyToAssign = strategyRepository.findById(tradeUpdate.getStrategyId()).orElseThrow(
                () -> new EntityNotFoundException("Strategy with id: " + tradeUpdate.getStrategyId() + " not found")
        );
        trade.setStrategy(strategyToAssign);
        return tradeMapper.toTradeListDto(tradeRepository.save(trade));
    }

    @Override
    public List<TradeSummaryDto> findByFilter(User user, TradeFindDto tradeFind) {
        List<Trade> trades = tradeRepository.findByParams(
                user.getAccountId(),
                tradeFind.getTradeDateFrom(),
                tradeFind.getTradeDateTo(),
                tradeFind.getTagged(),
                tradeFind.getSymbol(),
                tradeFind.getAssetCategory());
        return trades.stream().map(tradeMapper::toTradeListDto).collect(Collectors.toList());
    }

    @Override
    public UpdateReport<Trade> addOrSkip(User user, List<Trade> trades) {
        // Init lists
        List<Trade> tradesToAdd = new ArrayList<>();
        List<Trade> tradesToSkip = new ArrayList<>();

        // Check if trade already exists in the database
        for (Trade t : trades) {
            if (tradeRepository.existsByAccountIdAndIbOrderId(user.getAccountId(), t.getIbOrderId())) {
                tradesToSkip.add(t);
            } else {
                tradesToAdd.add(t);
            }
        }
        return UpdateReport.<Trade>builder()
                .added(this.saveAll(user, tradesToAdd))
                .skipped(tradesToSkip).build();
    }

    @Override
    public List<Trade> saveAll(User user, List<Trade> trades) {
        if (!trades.stream().allMatch(t -> t.getAccountId().equals(user.getAccountId()))) {
            throw new InvalidUserDataException();
        }
        try {
            return tradeRepository.saveAll(trades);
        } catch (Exception exc) {
            throw new UnableToSaveEntitiesException(exc.getMessage());
        }
    }
}
