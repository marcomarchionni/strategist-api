package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Trade;

import java.time.LocalDate;
import java.util.List;

public interface TradeService {

    boolean saveAll(List<Trade> trades);

    List<Trade> findAll();

    Trade updateStrategyId(Trade trade);

    boolean tradeDoesNotExist(Long id);

    List<Trade> findWithParameters(LocalDate startDate, LocalDate endDate, Boolean tagged, String symbol, String assetCategory);
}
