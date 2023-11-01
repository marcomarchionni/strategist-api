package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.TradeSummaryDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TradeMapperImpl implements TradeMapper {

    ModelMapper modelMapper;

    public TradeMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TradeSummaryDto toTradeListDto(Trade trade) {
        return modelMapper.map(trade, TradeSummaryDto.class);
    }

    @Override
    public Trade toTrade(FlexQueryResponseDto.Order orderDto) {
        return modelMapper.map(orderDto, Trade.class);
    }
}
