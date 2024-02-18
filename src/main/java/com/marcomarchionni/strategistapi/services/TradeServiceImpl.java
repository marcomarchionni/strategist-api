package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.accessservice.TradeAccessService;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.Trade;
import com.marcomarchionni.strategistapi.dtos.request.TradeFind;
import com.marcomarchionni.strategistapi.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummary;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateReport;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.strategistapi.mappers.TradeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final TradeAccessService tradeAccessService;
    private final StrategyAccessService strategyAccessService;
    private final TradeMapper tradeMapper;

    @Override
    public TradeSummary updateStrategyId(UpdateStrategyDto tradeUpdate) {

        Trade trade = tradeAccessService.findById(tradeUpdate.getId()).orElseThrow(
                () -> new EntityNotFoundException(Trade.class, tradeUpdate.getId())
        );
        Strategy strategyToAssign = strategyAccessService.findById(tradeUpdate.getStrategyId()).orElseThrow(
                () -> new EntityNotFoundException(Strategy.class, tradeUpdate.getStrategyId())
        );
        trade.setStrategy(strategyToAssign);
        return tradeMapper.toTradeListDto(tradeAccessService.save(trade));
    }

    @Override
    public List<TradeSummary> findByFilter(TradeFind tradeFind) {
        List<Trade> trades = tradeAccessService.findByParams(
                tradeFind.getTradeDateFrom(),
                tradeFind.getTradeDateTo(),
                tradeFind.getTagged(),
                tradeFind.getSymbol(),
                tradeFind.getAssetCategory());
        return trades.stream().map(tradeMapper::toTradeListDto).collect(Collectors.toList());
    }

    @Override
    public UpdateReport<Trade> updateTrades(List<Trade> trades) {

        if (trades.isEmpty()) {
            return UpdateReport.<Trade>builder().build();
        }

        // Init lists
        List<Trade> tradesToAdd = new ArrayList<>();
        List<Trade> tradesToSkip = new ArrayList<>();

        // Check if trade already exists in the database
        for (Trade t : trades) {
            if (tradeAccessService.existsByIbOrderId(t.getIbOrderId())) {
                tradesToSkip.add(t);
            } else {
                tradesToAdd.add(t);
            }
        }
        return UpdateReport.<Trade>builder()
                .added(this.saveAll(tradesToAdd))
                .skipped(tradesToSkip).build();
    }

    @Override
    public List<Trade> saveAll(List<Trade> trades) {
        try {
            return tradeAccessService.saveAll(trades);
        } catch (Exception exc) {
            throw new UnableToSaveEntitiesException(exc.getMessage());
        }
    }
}