package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.Strategy;
import com.marcomarchionni.ibportfolio.model.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.DividendListDto;
import com.marcomarchionni.ibportfolio.model.mapping.DividendMapper;
import com.marcomarchionni.ibportfolio.model.mapping.DividendMapperImpl;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DividendServiceImplTest {

    @Mock
    DividendRepository dividendRepository;
    @Mock
    StrategyRepository strategyRepository;
    DividendMapper dividendMapper;
    DividendService dividendService;
    List<Dividend> dividends;
    Dividend dividend;
    Strategy strategy;
    DividendFindDto dividendFind;

    @BeforeEach
    void setup() {
        dividends = getSampleDividends();
        dividend = getSampleClosedDividend();
        strategy = getSampleStrategy();
        dividendFind = getSampleDividendCriteria();
        dividendMapper = new DividendMapperImpl(new ModelMapper());
        dividendService = new DividendServiceImpl(dividendRepository, strategyRepository, dividendMapper);
    }


    @Test
    void findByParams() {

        when(dividendRepository.findByParams(any(), any(), any(), any(), any(), any())).thenReturn(dividends);

        List<DividendListDto> foundDividends = dividendService.findByFilter(
                dividendFind);

        assertEquals(dividends.size(), foundDividends.size());
    }

    @Test
    void updateStrategyId() {

        Dividend expectedDividend = getSampleClosedDividend();
        UpdateStrategyDto dividendUpdate = UpdateStrategyDto.builder()
                .id(expectedDividend.getId()).strategyId(strategy.getId()).build();
        expectedDividend.setStrategy(strategy);


        when(dividendRepository.findById(dividendUpdate.getId())).thenReturn(Optional.of(dividend));
        when(strategyRepository.findById(dividendUpdate.getStrategyId())).thenReturn(Optional.of(strategy));
        when(dividendRepository.save(expectedDividend)).thenReturn(expectedDividend);

        DividendListDto actualDividendDto = dividendService.updateStrategyId(dividendUpdate);

        assertNotNull(actualDividendDto);
        assertEquals(dividendUpdate.getId(), actualDividendDto.getId());
        assertEquals(dividendUpdate.getStrategyId(), actualDividendDto.getStrategyId());
    }
}