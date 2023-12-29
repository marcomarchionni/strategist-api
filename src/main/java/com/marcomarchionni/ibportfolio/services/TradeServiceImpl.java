package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.accessservice.StrategyAccessService;
import com.marcomarchionni.ibportfolio.accessservice.TradeAccessService;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.TradeSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.TradeMapper;
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
    public TradeSummaryDto updateStrategyId(UpdateStrategyDto tradeUpdate) {

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
    public List<TradeSummaryDto> findByFilter(TradeFindDto tradeFind) {
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
