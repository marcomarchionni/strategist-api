package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.TradeListDto;

public interface TradeMapper {

    TradeListDto toTradeListDto(Trade trade);

    Trade toTrade(FlexQueryResponseDto.Order orderDto);
}
