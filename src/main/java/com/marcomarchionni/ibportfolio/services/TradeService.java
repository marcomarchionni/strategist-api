package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Trade;

import java.time.LocalDate;
import java.util.List;

public interface TradeService {

    boolean saveAll(List<Trade> trades);

    Trade updateStrategyId(Trade trade);

    List<Trade> findWithParameters(LocalDate startDate, LocalDate endDate, Boolean tagged, String symbol, String assetCategory);
}
