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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DividendServiceImplTest {

    @Mock
    DividendRepository dividendRepository;
    @Mock
    StrategyRepository strategyRepository;
    DividendMapper dividendMapper;
    DividendService dividendService;
    List<Dividend> userDividends;
    Dividend dividend;
    Strategy strategy;
    DividendFindDto dividendFind;
    User user;

    @BeforeEach
    void setup() {
        user = getSampleUser();
        userDividends = getSampleDividends();
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
        // setup mocks
        when(dividendRepository.findByParams(
                user.getAccountId(),
                dividendFind.getExDateFrom(),
                dividendFind.getExDateTo(),
                dividendFind.getPayDateFrom(),
                dividendFind.getPayDateTo(),
                dividendFind.getTagged(),
                dividendFind.getSymbol())).thenReturn(userDividends);

        // execute method
        List<DividendSummaryDto> foundDividends = dividendService.findByFilter(
                user, dividendFind);

        // assertions
        assertEquals(this.userDividends.size(), foundDividends.size());
        assertEquals(this.userDividends.get(0).getAccountId(), user.getAccountId());
    }

    @Test
    void updateStrategyId() {
        // setup test data
        UpdateStrategyDto dividendUpdate = UpdateStrategyDto.builder()
                .id(dividend.getId()).strategyId(strategy.getId()).build();

        // setup mocks
        when(dividendRepository.findByIdAndAccountId(dividend.getId(), user.getAccountId())).thenReturn(Optional.of(dividend));
        when(strategyRepository.findByIdAndAccountId(strategy.getId(), user.getAccountId())).thenReturn(Optional.of(strategy));
        when(dividendRepository.save(any(Dividend.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // execute method
        DividendSummaryDto actualDividendDto = dividendService.updateStrategyId(user, dividendUpdate);

        // assertions
        assertNotNull(actualDividendDto);
        assertEquals(dividend.getId(), actualDividendDto.getId());
        assertEquals(strategy.getId(), actualDividendDto.getStrategyId());
    }

    @Test
    void updateDividends() {
        //// setup data
        // setup existing closed dividend
        Dividend EBAYClosedDividend = getEBAYClosedDividend();

        // setup existing open dividend
        Dividend NKEOpenDividend = getNKEOpenDividend();
        NKEOpenDividend.setStrategy(getSampleStrategy());
        List<Dividend> existingOpenDividends = List.of(NKEOpenDividend);

        // setup new open dividend
        Dividend FDXOpenDividend = getFDXOpenDividend();
        List<Dividend> newOpenDividends = List.of(FDXOpenDividend);

        // setup new closed dividends
        List<Dividend> newClosedDividends = List.of(getNKEClosedDividend(), getEBAYClosedDividend());

        // setup mocks
        when(dividendRepository.findOpenDividendsByAccountId(user.getAccountId())).thenReturn(existingOpenDividends);
        when(dividendRepository.existsByAccountIdAndActionId(user.getAccountId(), EBAYClosedDividend.getActionId())).thenReturn(true);
        when(dividendRepository.existsByAccountIdAndActionId(user.getAccountId(), FDXOpenDividend.getActionId())).thenReturn(false);
        when(dividendRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
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
        // setup data
        //// setup existing closed dividend
        Dividend EBAYClosedDividend = getEBAYClosedDividend();
        EBAYClosedDividend.setStrategy(getSampleStrategy());

        //// setup new closed dividends
        Dividend NKEClosedDividend = getNKEClosedDividend();
        Dividend newEBAYClosedDividend = getEBAYClosedDividend();
        List<Dividend> newClosedDividends = List.of(NKEClosedDividend, newEBAYClosedDividend);

        //// setup mocks
        when(dividendRepository.existsByAccountIdAndActionId(user.getAccountId(),
                EBAYClosedDividend.getActionId())).thenReturn(true);
        when(dividendRepository.existsByAccountIdAndActionId(user.getAccountId(), NKEClosedDividend.getActionId())).thenReturn(false);
        when(dividendRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // execute method
        UpdateReport<Dividend> result = dividendService.addOrSkip(user, newClosedDividends);

        // assertions
        verify(dividendRepository, times(1)).existsByAccountIdAndActionId(user.getAccountId(),
                newEBAYClosedDividend.getActionId());
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