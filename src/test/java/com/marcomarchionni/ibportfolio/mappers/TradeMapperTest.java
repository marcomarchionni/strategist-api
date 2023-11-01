package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.TradeSummaryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleStrategy;
import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleTrade;
import static org.junit.jupiter.api.Assertions.*;

class TradeMapperTest {

    ModelMapper mapper;

    TradeMapperImpl tradeMapper;

    @BeforeEach
    void setup() {
        ModelMapperConfig mapperConfig = new ModelMapperConfig();
        mapper = mapperConfig.modelMapper();
        tradeMapper = new TradeMapperImpl(mapper);
    }

    @Test
    void toTradeListDto() {
        Strategy strategy = getSampleStrategy();
        Trade trade = getSampleTrade();
        trade.setStrategy(strategy);

        TradeSummaryDto tradeSummaryDto = tradeMapper.toTradeListDto(trade);

        assertEquals(trade.getId(), tradeSummaryDto.getId());
        assertEquals(trade.getStrategy().getName(), tradeSummaryDto.getStrategyName());
    }

    @Test
    void toTrade() {
        FlexQueryResponseDto.Order order = new FlexQueryResponseDto.Order();
        order.setCurrency("USD");
        order.setAssetCategory("STK");
        order.setSymbol("CGNX");
        order.setConid(370695082L);
        order.setIbOrderID(339580463L);

        Trade trade = mapper.map(order, Trade.class);
        assertNotNull(trade);
        assertEquals(order.getIbOrderID(), trade.getId());
        assertEquals(order.getIbOrderID(), trade.getIbOrderId());
        assertNull(trade.getStrategy());
    }
}