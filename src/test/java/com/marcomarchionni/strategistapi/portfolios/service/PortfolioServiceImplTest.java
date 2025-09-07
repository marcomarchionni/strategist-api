package com.marcomarchionni.strategistapi.portfolios.service;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.portfolios.mapper.PortfolioMapper;
import com.marcomarchionni.strategistapi.portfolios.mapper.PortfolioMapperImpl;
import com.marcomarchionni.strategistapi.portfolios.repo.PortfolioRepository;
import com.marcomarchionni.strategistapi.services.UserService;
import com.marcomarchionni.strategistapi.services.specifications.SimplePortfolioSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static com.marcomarchionni.strategistapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceImplTest {

    @Mock
    PortfolioRepository portfolioRepository;
    @Mock
    Specification<Portfolio> spec;
    @Mock
    SimplePortfolioSpecification portfolioSpecification;
    @Mock
    UserService userService;
    PortfolioMapper portfolioMapper;
    PortfolioService portfolioService;
    User user;
    Portfolio userPortfolio;

    @BeforeEach
    void setup() {
        portfolioMapper = new PortfolioMapperImpl(new ModelMapper());
        portfolioService = new PortfolioServiceImpl(userService, portfolioMapper,
                portfolioRepository, portfolioSpecification);

        user = getSampleUser();
        userPortfolio = getSamplePortfolio("MFStockAdvisor");
    }

    @Test
    void findAllWithCount() {
        // Setup test data
        String accountId = user.getAccountId();
        List<Portfolio> portfolios = getSamplePortfolios();
        portfolios.forEach(portfolio -> portfolio.setAccountId(accountId));
        FindAllReq findReq = FindAllReq.builder()
                .skip(0)
                .top(10)
                .build();

        // Create a mock Page
        Page<Portfolio> portfolioPage = new PageImpl<>(portfolios); // Mocked Page

        // Setup mocks
        when(portfolioRepository.findAll(ArgumentMatchers.<Specification<Portfolio>>any(), any(Pageable.class)))
                .thenReturn(portfolioPage);
        when(portfolioRepository.count(ArgumentMatchers.<Specification<Portfolio>>any()))
                .thenReturn((long) portfolios.size());
        when(portfolioSpecification.buildSpecification(accountId, null, null, null, null)).thenReturn(spec);
        when(userService.getUserAccountId()).thenReturn(accountId);

        // Execute service
        ApiResponse<PortfolioSummary> response = portfolioService.findAllWithCount(findReq);

        // Verify results
        assertEquals(portfolios.size(), response.getResult().size());
        assertEquals(portfolios.size(), response.getCount());
    }

    @Test
    void findByIdSuccess() {
        // Setup test data
        Long portfolioId = userPortfolio.getId();

        // Setup mocks
        when(portfolioRepository.findById(portfolioId)).thenReturn(Optional.of(userPortfolio));

        // Execute service
        PortfolioDetail actualPortfolioDto = portfolioService.findById(portfolioId);

        // Verify results
        assertEquals(actualPortfolioDto.getId(), userPortfolio.getId());
        assertEquals(actualPortfolioDto.getName(), userPortfolio.getName());
    }

    @Test
    void findByIdException() {
        // Setup test data
        Long unknownPortfolioId = 1L;

        // Setup mocks
        when(portfolioRepository.findById(unknownPortfolioId)).thenReturn(Optional.empty());

        // Execute service and verify exception
        assertThrows(EntityNotFoundException.class, () -> portfolioService.findById(unknownPortfolioId));
    }

    @Test
    void createPortfolioSuccess() {
        // Setup test data
        PortfolioSave portfolioSave = PortfolioSave.builder().name("NewPortfolioName").build();
        String portfolioName = portfolioSave.getName();
        String accountId = user.getAccountId();

        // Setup mocks
        when(portfolioRepository.findByAccountIdAndName(accountId, portfolioName)).thenReturn(Optional.empty());
        when(portfolioRepository.save(any(Portfolio.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userService.getUserAccountId()).thenReturn(accountId);

        // Execute service
        PortfolioSummary createdPortfolioDto = portfolioService.create(portfolioSave);

        // Verify results
        assertNotNull(createdPortfolioDto);
        assertEquals(portfolioName, createdPortfolioDto.getName());
        assertEquals(user.getAccountId(), createdPortfolioDto.getAccountId());
    }

    @Test
    void updatePortfolioSuccess() {
        // Setup test data
        String accountId = user.getAccountId();
        Long portfolioId = userPortfolio.getId();
        var portfolioUpdate = PortfolioSave.builder().id(portfolioId).name("NewName").description("New Description")
                .build();

        // Setup mocks
        when(portfolioRepository.findById(portfolioId)).thenReturn(Optional.of(userPortfolio));
        when(userService.getUserAccountId()).thenReturn(accountId);
        when(portfolioRepository.findByAccountIdAndName(accountId, "NewName")).thenReturn(Optional.empty());
        when(portfolioRepository.save(any(Portfolio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execute service
        PortfolioSummary actualPortfolioDto = portfolioService.update(portfolioUpdate);

        // Verify results
        assertNotNull(actualPortfolioDto);
        assertEquals("NewName", actualPortfolioDto.getName());
        assertEquals("New Description", actualPortfolioDto.getDescription());
        assertEquals(portfolioId, actualPortfolioDto.getId());
        assertEquals(accountId, actualPortfolioDto.getAccountId());
    }

    @Test
    void deleteByIdSuccess() {
        // setup test data
        Long portfolioId = userPortfolio.getId();

        // setup mocks
        when(portfolioRepository.findById(portfolioId)).thenReturn(Optional.of(userPortfolio));
        doNothing().when(portfolioRepository).delete(userPortfolio);

        // execute service
        assertDoesNotThrow(() -> portfolioService.deleteById(portfolioId));

        // verify results
        verify(portfolioRepository).findById(portfolioId);
        verify(portfolioRepository).delete(userPortfolio);
    }

    @Test
    void deleteByIdException() {
        // setup test data
        Long unknownPortfolioId = 1L;

        // setup mocks
        when(portfolioRepository.findById(unknownPortfolioId)).thenReturn(Optional.empty());

        // execute service and verify exception
        assertThrows(EntityNotFoundException.class, () -> portfolioService.deleteById(unknownPortfolioId));
    }
}
