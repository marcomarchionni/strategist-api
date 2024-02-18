package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.config.ModelMapperConfig;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.Trade;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.response.TradeSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleStrategy;
import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleTrade;
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

        TradeSummary tradeSummary = tradeMapper.toTradeListDto(trade);

        assertEquals(trade.getId(), tradeSummary.getId());
        assertEquals(trade.getStrategy().getName(), tradeSummary.getStrategyName());
    }

    @Test
    void toTrade() {
        FlexQueryResponseDto.Order order = FlexQueryResponseDto.Order.builder()
                .currency("USD")
                .assetCategory("STK")
                .symbol("CGNX")
                .conid(370695082L)
                .ibOrderID(339580463L)
                .build();

        Trade trade = mapper.map(order, Trade.class);

        assertNotNull(trade);
        assertNull(trade.getId());
        assertEquals(order.getIbOrderID(), trade.getIbOrderId());
        assertNull(trade.getStrategy());
    }
}