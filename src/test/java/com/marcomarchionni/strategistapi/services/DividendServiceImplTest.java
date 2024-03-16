package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.DividendAccessService;
import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.config.ModelMapperConfig;
import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.DividendFind;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummary;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateReport;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.mappers.DividendMapper;
import com.marcomarchionni.strategistapi.mappers.DividendMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.strategistapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DividendServiceImplTest {

    @Mock
    DividendAccessService dividendAccessService;
    @Mock
    StrategyAccessService strategyAccessService;
    DividendMapper dividendMapper;
    DividendService dividendService;
    List<Dividend> userDividends;
    Dividend dividend;
    Strategy strategy;
    DividendFind dividendFind;
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
        dividendService = new DividendServiceImpl(dividendAccessService, strategyAccessService, dividendMapper);
    }


    @Test
    void findByParams() {
        // setup mocks
        when(dividendAccessService.findByParams(
                dividendFind.getExDateAfter(),
                dividendFind.getExDateBefore(),
                dividendFind.getPayDateAfter(),
                dividendFind.getPayDateBefore(),
                dividendFind.getTagged(),
                dividendFind.getSymbol())).thenReturn(userDividends);

        // execute method
        List<DividendSummary> foundDividends = dividendService.findByFilter(
                dividendFind);

        // assertions
        assertEquals(this.userDividends.size(), foundDividends.size());
        assertEquals(this.userDividends.get(0).getAccountId(), user.getAccountId());
    }

    @Test
    void updateStrategyId() {
        // setup test data
        StrategyAssign dividendUpdate = StrategyAssign.builder()
                .id(dividend.getId()).strategyId(strategy.getId()).build();

        // setup mocks
        when(dividendAccessService.findById(dividend.getId())).thenReturn(Optional.of(dividend));
        when(strategyAccessService.findById(strategy.getId())).thenReturn(Optional.of(strategy));
        when(dividendAccessService.save(any(Dividend.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // execute method
        DividendSummary actualDividendDto = dividendService.updateStrategyId(dividendUpdate);

        // assertions
        assertNotNull(actualDividendDto);
        assertEquals(dividend.getId(), actualDividendDto.getId());
        assertEquals(strategy.getId(), actualDividendDto.getStrategyId());
    }

    @Test
    void updateStrategyIdNullSuccess() {
        // setup test data
        StrategyAssign dividendUpdate = StrategyAssign.builder()
                .id(dividend.getId()).strategyId(null).build();

        // setup mocks
        when(dividendAccessService.findById(dividend.getId())).thenReturn(Optional.of(dividend));
        when(dividendAccessService.save(any(Dividend.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // execute method
        DividendSummary actualDividendDto = dividendService.updateStrategyId(dividendUpdate);

        // assertions
        assertNotNull(actualDividendDto);
        assertEquals(dividend.getId(), actualDividendDto.getId());
        assertNull(actualDividendDto.getStrategyId());
    }

    @Test
    void updateStrategyIdException() {
        // setup test data
        StrategyAssign dividendUpdate = StrategyAssign.builder()
                .id(dividend.getId()).strategyId(strategy.getId()).build();

        // setup mocks
        when(dividendAccessService.findById(dividend.getId())).thenReturn(Optional.of(dividend));
        when(strategyAccessService.findById(strategy.getId())).thenReturn(Optional.empty());

        // execute method
        assertThrows(EntityNotFoundException.class, () -> dividendService.updateStrategyId(dividendUpdate));
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

        // new dividends
        List<Dividend> newDividends = List.of(getFDXOpenDividend(), getNKEClosedDividend(), getEBAYClosedDividend());

        // setup mocks
        when(dividendAccessService.findOpenDividends()).thenReturn(existingOpenDividends);
        when(dividendAccessService.existsByActionId(EBAYClosedDividend.getActionId())).thenReturn(true);
        when(dividendAccessService.existsByActionId(getFDXOpenDividend().getActionId())).thenReturn(false);
        when(dividendAccessService.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
        when(dividendAccessService.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // execute method
        UpdateReport<DividendSummary> result = dividendService.updateDividends(newDividends);

        // assertions
        assertEquals(1, result.getAdded().size());
        assertEquals(1, result.getMerged().size());
        assertEquals(1, result.getSkipped().size());
        assertEquals("FDX", result.getAdded().get(0).getSymbol());
        assertEquals("NKE", result.getMerged().get(0).getSymbol());
        assertEquals("EBAY", result.getSkipped().get(0).getSymbol());
    }
}