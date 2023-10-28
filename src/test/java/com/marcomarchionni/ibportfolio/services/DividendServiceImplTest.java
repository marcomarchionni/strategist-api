package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendListDto;
import com.marcomarchionni.ibportfolio.mappers.DividendMapper;
import com.marcomarchionni.ibportfolio.mappers.DividendMapperImpl;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    @Test
    void updateDividends() {

        // setup existing closed dividend
        Dividend EBAYClosedDividend = getEBAYClosedDividend();

        // setup existing open dividend
        Dividend NKEOpenDividend = getNKEOpenDividend();
        NKEOpenDividend.setStrategy(getSampleStrategy());
        List<Dividend> existingOpenDividends = List.of(NKEOpenDividend);

        // setup new open dividend
        List<Dividend> newOpenDividends = List.of(getFDXOpenDividend());

        // setup new closed dividends
        Dividend NKEClosedDividend = getNKEClosedDividend();
        NKEClosedDividend.setStrategy(getSampleStrategy());
        List<Dividend> newClosedDividends = List.of(getNKEClosedDividend(), getEBAYClosedDividend());

        // setup mocks
        when(dividendRepository.findOpenDividends()).thenReturn(existingOpenDividends);
        when(dividendRepository.existsById(EBAYClosedDividend.getId())).thenReturn(true);
        ArgumentCaptor<List<Dividend>> captor = ArgumentCaptor.forClass(List.class);
        when(dividendRepository.saveAll(captor.capture())).thenAnswer(invocation -> captor.getValue());

        // expected result
        List<Dividend> expectedSavedDividends = List.of(getFDXOpenDividend(), NKEClosedDividend);

        // execute method
        List<Dividend> actualSavedDividends = dividendService.updateDividends(newOpenDividends, newClosedDividends);

        // assertions
        assertEquals(expectedSavedDividends.size(), actualSavedDividends.size());
        assertEquals(expectedSavedDividends.get(0).getSymbol(), actualSavedDividends.get(0).getSymbol());
        assertEquals(actualSavedDividends.get(1).getStrategy(), getSampleStrategy());
    }

    @Test
    void saveOrSkip() {
        // setup existing closed dividend
        Dividend EBAYClosedDividend = getEBAYClosedDividend();
        EBAYClosedDividend.setStrategy(getSampleStrategy());

        // setup new closed dividends
        Dividend NKEClosedDividend = getNKEClosedDividend();
        Dividend newEBAYClosedDividend = getEBAYClosedDividend();
        List<Dividend> newClosedDividends = List.of(NKEClosedDividend, newEBAYClosedDividend);

        // setup mocks
        when(dividendRepository.existsById(EBAYClosedDividend.getId())).thenReturn(true);
        when(dividendRepository.existsById(NKEClosedDividend.getId())).thenReturn(false);
        ArgumentCaptor<List<Dividend>> captor = ArgumentCaptor.forClass(List.class);
        when(dividendRepository.saveAll(captor.capture())).thenAnswer(invocation -> captor.getValue());

        // execute method
        List<Dividend> actualSavedDividends = dividendService.saveOrIgnore(newClosedDividends);

        // assertions
        assertEquals(1, actualSavedDividends.size());
        assertEquals(NKEClosedDividend.getSymbol(), actualSavedDividends.get(0).getSymbol());

    }
}