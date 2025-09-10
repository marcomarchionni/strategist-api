package com.marcomarchionni.strategistapi.services;

import static com.marcomarchionni.strategistapi.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.marcomarchionni.strategistapi.accessservice.PortfolioAccessService;
import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFind;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.StrategyUpdate;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetail;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;
import com.marcomarchionni.strategistapi.strategies.mapper.StrategyMapper;
import com.marcomarchionni.strategistapi.strategies.mapper.StrategyMapperImpl;
import com.marcomarchionni.strategistapi.strategies.repo.StrategyRepository;
import com.marcomarchionni.strategistapi.strategies.service.StrategyServiceImpl;
import com.marcomarchionni.strategistapi.strategies.spec.SimpleStrategySpecification;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class StrategyServiceImplTest {

  @Mock StrategyAccessService strategyAccessService;
  @Mock PortfolioAccessService portfolioAccessService;
  @Mock StrategyRepository strategyRepository;
  @Mock UserService userService;
  @Mock SimpleStrategySpecification strategySpecification;
  StrategyMapper strategyMapper;
  StrategyServiceImpl strategyService;
  List<Strategy> userStrategies;
  User user;
  Strategy userStrategy;

  @BeforeEach
  void setup() {
    strategyMapper = new StrategyMapperImpl(new ModelMapper());
    strategyService =
        new StrategyServiceImpl(
            strategyAccessService,
            portfolioAccessService,
            strategyMapper,
            strategyRepository,
            userService,
            strategySpecification);

    user = getSampleUser();
    userStrategy = getSampleStrategy();
    userStrategies = getSampleStrategies();
  }

  @Test
  void findByParams() {
    // setup
    StrategyFind strategyFind = StrategyFind.builder().build();
    when(strategyAccessService.findByParams(strategyFind.getName())).thenReturn(userStrategies);

    // execute
    List<StrategySummary> actualStrategies = strategyService.findByFilter(strategyFind);

    // verify
    assertNotNull(actualStrategies);
    assertTrue(
        actualStrategies.stream().allMatch(dto -> dto.getAccountId().equals(user.getAccountId())));
  }

  @Test
  void findByIdSuccess() {
    // setup
    userStrategy.getTrades().add(getSampleTrade());
    when(strategyAccessService.findById(userStrategy.getId()))
        .thenReturn(Optional.of(userStrategy));

    // execute
    StrategyDetail strategyDetail = strategyService.findById(userStrategy.getId());

    // verify
    assertNotNull(strategyDetail);
    assertEquals(userStrategy.getId(), strategyDetail.getId());
    assertEquals(userStrategy.getTrades().size(), strategyDetail.getTrades().size());
  }

  @Test
  void create() {
    // setup test data
    Portfolio userPortfolio = getSamplePortfolio("MyPortfolio");
    StrategyCreate strategyCreate =
        StrategyCreate.builder().name("ZM long").portfolioId(userPortfolio.getId()).build();

    // setup mocks
    when(portfolioAccessService.findById(userPortfolio.getId()))
        .thenReturn(Optional.of(userPortfolio));
    when(strategyAccessService.save(any(Strategy.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // execute
    StrategyDetail actualStrategy = strategyService.create(strategyCreate);

    // verify
    assertNotNull(actualStrategy);
    assertEquals(strategyCreate.getName(), actualStrategy.getName());
    assertEquals(userPortfolio.getId(), actualStrategy.getPortfolioId());
  }

  @Test
  void update() {
    // setup test data
    StrategyUpdate strategyUpdate =
        StrategyUpdate.builder().id(userStrategy.getId()).name("NewName").build();

    // setup mocks
    when(strategyAccessService.findById(userStrategy.getId()))
        .thenReturn(Optional.of(userStrategy));
    when(strategyAccessService.save(any(Strategy.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // execute
    StrategyDetail renamedStrategy = strategyService.update(strategyUpdate);

    // verify
    assertNotNull(renamedStrategy);
    assertEquals(userStrategy.getId(), renamedStrategy.getId());
    assertEquals(strategyUpdate.getName(), renamedStrategy.getName());
  }

  @Test
  void findAllWithCount_returnsPagedStrategies() {
    // setup
    StrategyFindAllReq findReq =
        StrategyFindAllReq.builder()
            .skip(0)
            .top(10)
            .orderBy("name")
            .name(null)
            .description(null)
            .portfolioName(null)
            .createdAfter(null)
            .createdBefore(null)
            .build();

    when(userService.getUserAccountId()).thenReturn(user.getAccountId());
    Specification<Strategy> spec = (root, query, cb) -> cb.conjunction();
    when(strategySpecification.buildSpecification(
            any(String.class), any(), any(), any(), any(), any()))
        .thenReturn(spec);

    List<Strategy> strategies = List.of(userStrategy);
    Page<Strategy> page = new PageImpl<>(strategies);
    when(strategyRepository.findAll(
            ArgumentMatchers.<Specification<Strategy>>any(), any(Pageable.class)))
        .thenReturn(page);
    when(strategyRepository.count(ArgumentMatchers.<Specification<Strategy>>any()))
        .thenReturn((long) strategies.size());

    // execute
    ApiResponse<StrategySummary> response = strategyService.findAllWithCount(findReq);

    // verify
    assertNotNull(response);
    assertEquals(1, response.getResult().size());
    assertEquals(1, response.getCount());
    assertEquals(userStrategy.getId(), response.getResult().get(0).getId());
  }

  @Test
  void deleteByIdSuccess() {
    // setup entities
    Long strategyId = userStrategy.getId();

    // setup mocks
    when(strategyAccessService.findById(strategyId)).thenReturn(Optional.of(userStrategy));
    doNothing().when(strategyAccessService).delete(userStrategy);

    // execute
    strategyService.deleteById(strategyId);

    // verify
    verify(strategyAccessService, times(1)).findById(strategyId);
    verify(strategyAccessService, times(1)).delete(userStrategy);
  }
}
