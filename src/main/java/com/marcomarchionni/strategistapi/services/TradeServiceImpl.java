package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.accessservice.TradeAccessService;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.Trade;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.request.TradeFind;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummary;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateReport;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.strategistapi.mappers.TradeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final TradeAccessService tradeAccessService;
    private final StrategyAccessService strategyAccessService;
    private final TradeMapper tradeMapper;

    @Override
    public TradeSummary updateStrategyId(StrategyAssign strategyAssign) {

        Trade trade = tradeAccessService.findById(strategyAssign.getId()).orElseThrow(
                () -> new EntityNotFoundException(Trade.class, strategyAssign.getId())
        );

        Strategy strategyToAssign = getStrategyToAssign(strategyAssign.getStrategyId());

        trade.setStrategy(strategyToAssign);
        return tradeMapper.toTradeSummary(tradeAccessService.save(trade));
    }

    private Strategy getStrategyToAssign(Long strategyId) {
        return Optional.ofNullable(strategyId)
                .map(id -> strategyAccessService.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(Strategy.class, id)))
                .orElse(null);
    }

    @Override
    public List<TradeSummary> findByFilter(TradeFind tradeFind) {
        List<Trade> trades = tradeAccessService.findByParams(
                tradeFind.getTradeDateAfter(),
                tradeFind.getTradeDateBefore(),
                tradeFind.getTagged(),
                tradeFind.getSymbol(),
                tradeFind.getAssetCategory());
        return trades.stream().map(tradeMapper::toTradeSummary).collect(Collectors.toList());
    }

    @Override
    public UpdateReport<TradeSummary> updateTrades(List<Trade> trades) {

        if (trades.isEmpty()) {
            return UpdateReport.<TradeSummary>builder().build();
        }

        // Init lists
        List<Trade> tradesToAdd = new ArrayList<>();
        List<TradeSummary> tradesToSkip = new ArrayList<>();

        // Check if trade already exists in the database
        for (Trade t : trades) {
            if (tradeAccessService.existsByIbOrderId(t.getIbOrderId())) {
                tradesToSkip.add(tradeMapper.toTradeSummary(t));
            } else {
                tradesToAdd.add(t);
            }
        }

        return UpdateReport.<TradeSummary>builder()
                .added(this.saveAll(tradesToAdd))
                .skipped(tradesToSkip).build();
    }

    @Override
    public List<TradeSummary> saveAll(List<Trade> trades) {
        try {
            List<Trade> savedTrades = tradeAccessService.saveAll(trades);
            return savedTrades.stream().map(tradeMapper::toTradeSummary).toList();
        } catch (Exception exc) {
            throw new UnableToSaveEntitiesException(exc.getMessage());
        }
    }
}
