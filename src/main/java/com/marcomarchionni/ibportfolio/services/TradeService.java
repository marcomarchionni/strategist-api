package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.TradeCriteriaDto;

import java.time.LocalDate;
import java.util.List;

public interface TradeService {

    void saveAll(List<Trade> trades);

    Trade updateStrategyId(Trade trade);

    List<Trade> findWithParameters(TradeCriteriaDto tradeCriteria);
}
