package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.domain.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.TradeFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.TradeListDto;

import java.util.List;

public interface TradeService {

    void saveAll(List<Trade> trades);

    TradeListDto updateStrategyId(UpdateStrategyDto trade);

    List<TradeListDto> findByParams(TradeFindDto tradeCriteria);
}
