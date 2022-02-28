package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Trade;

import java.util.List;

public interface TradeService {

    boolean saveTrades(List<Trade> trades);
}
