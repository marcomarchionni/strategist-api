package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.config.ModelMapperConfig;
import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidDataException;
import com.marcomarchionni.ibportfolio.mappers.DividendMapper;
import com.marcomarchionni.ibportfolio.mappers.DividendMapperImpl;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        ModelMapper mapper = modelMapperConfig.modelMapper();
        dividendMapper = new DividendMapperImpl(mapper);
        dividendService = new DividendServiceImpl(dividendRepository, strategyRepository, dividendMapper);
    }


    @Test
    void findByParams() {

        User user = getSampleUser();
        List<Dividend> userDividends = dividends.stream().peek(d -> d.setAccountId(user.getAccountId())).toList();

        when(dividendRepository.findByParams(any(), any(), any(), any(), any(), any())).thenReturn(userDividends);

        List<DividendSummaryDto> foundDividends = dividendService.findByFilter(
                user, dividendFind);

        assertEquals(dividends.size(), foundDividends.size());
        assertEquals(dividends.get(0).getAccountId(), user.getAccountId());
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

        DividendSummaryDto actualDividendDto = dividendService.updateStrategyId(dividendUpdate);

        assertNotNull(actualDividendDto);
        assertEquals(dividendUpdate.getId(), actualDividendDto.getId());
        assertEquals(dividendUpdate.getStrategyId(), actualDividendDto.getStrategyId());
    }

    @Test
    void updateDividends() {
        // setup user
        User user = getSampleUser();

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
        List<Dividend> newClosedDividends = List.of(getNKEClosedDividend(), getEBAYClosedDividend());

        // setup mocks
        when(dividendRepository.findOpenDividends(user.getAccountId())).thenReturn(existingOpenDividends);
        when(dividendRepository.existsById(EBAYClosedDividend.getId())).thenReturn(true);
        when(dividendRepository.existsById(NKEClosedDividend.getId())).thenReturn(true);
        when(dividendRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // execute method
        UpdateReport<Dividend> result = dividendService.updateDividends(user, newOpenDividends, newClosedDividends);

        // assertions
        assertEquals(1, result.getAdded().size());
        assertEquals(1, result.getMerged().size());
        assertEquals(1, result.getSkipped().size());
        assertEquals("FDX", result.getAdded().get(0).getSymbol());
        assertEquals("NKE", result.getMerged().get(0).getSymbol());
        assertEquals("EBAY", result.getSkipped().get(0).getSymbol());
    }

    @Test
    void addOrSkip() {
        // setup user
        User user = getSampleUser();

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
        when(dividendRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // execute method
        UpdateReport<Dividend> result = dividendService.addOrSkip(user, newClosedDividends);

        // assertions
        assertEquals(1, result.getAdded().size());
        assertEquals(1, result.getSkipped().size());
        assertEquals("NKE", result.getAdded().get(0).getSymbol());
        assertEquals("EBAY", result.getSkipped().get(0).getSymbol());
    }

    @Test
    void addOrSkipInvalidDataException() {
        // setup user
        User user = getSampleUser();
        user.setAccountId("UNEXPECTED_ACCOUNT_ID");

        // setup new closed dividends
        List<Dividend> newClosedDividends = List.of(getNKEClosedDividend());

        // execute method
        assertThrows(InvalidDataException.class, () -> dividendService.addOrSkip(user, newClosedDividends));
    }
}