package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.Trade;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.request.TradeFind;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummary;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateReport;

import java.util.List;

public interface TradeService {

    List<Trade> saveAll(List<Trade> trades);

    TradeSummary updateStrategyId(StrategyAssign trade);

    List<TradeSummary> findByFilter(TradeFind tradeCriteria);

    UpdateReport<Trade> updateTrades(List<Trade> trades);
}
