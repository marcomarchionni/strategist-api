package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Trade;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummary;

public interface TradeMapper {

    TradeSummary toTradeSummary(Trade trade);

    Trade toTrade(FlexQueryResponseDto.Order orderDto);
}
