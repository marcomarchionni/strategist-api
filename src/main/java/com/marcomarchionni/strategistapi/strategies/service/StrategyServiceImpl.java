package com.marcomarchionni.strategistapi.strategies.service;

import com.marcomarchionni.strategistapi.accessservice.PortfolioAccessService;
import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFind;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetail;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.strategistapi.services.UserService;
import com.marcomarchionni.strategistapi.services.specifications.PagingUtil;
import com.marcomarchionni.strategistapi.strategies.mapper.StrategyMapper;
import com.marcomarchionni.strategistapi.strategies.repo.StrategyRepository;
import com.marcomarchionni.strategistapi.strategies.spec.SimpleStrategySpecification;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StrategyServiceImpl implements StrategyService {
  private final StrategyAccessService strategyAccessService;
  private final PortfolioAccessService portfolioAccessService;
  private final StrategyMapper strategyMapper;
  private final StrategyRepository strategyRepository;
  private final UserService userService;
  private final SimpleStrategySpecification strategySpecification;

  @Override
  public List<StrategySummary> findByFilter(StrategyFind strategyFind) {
    List<Strategy> strategies = strategyAccessService.findByParams(strategyFind.getName());
    return strategies.stream()
        .map(strategyMapper::toStrategySummaryDto)
        .collect(Collectors.toList());
  }

  @Override
  public ApiResponse<StrategySummary> findAllWithCount(FindAllReq findReq) {
    String accountId = userService.getUserAccountId();
    // Map portfolioName to JPA sort property portfolio.name if present
    String orderBy = findReq.getOrderBy();
    if (orderBy != null && orderBy.toLowerCase().startsWith("portfolioname")) {
      orderBy = orderBy.replaceFirst("(?i)portfolioname", "portfolio.name");
    }
    FindAllReq pageReq =
        FindAllReq.builder()
            .skip(findReq.getSkip())
            .top(findReq.getTop())
            .orderBy(orderBy)
            .name(findReq.getName())
            .description(findReq.getDescription())
            .createdAfter(findReq.getCreatedAfter())
            .createdBefore(findReq.getCreatedBefore())
            .build();
    Pageable pageable = PagingUtil.createPageable(pageReq);

    Specification<Strategy> spec =
        strategySpecification.buildSpecification(
            accountId,
            findReq.getName(),
            findReq.getDescription() // using description to carry portfolioName filter
            );

    Page<Strategy> page = strategyRepository.findAll(spec, pageable);
    long totalCount = strategyRepository.count(spec);

    return ApiResponse.<StrategySummary>builder()
        .result(page.stream().map(strategyMapper::toStrategySummaryDto).toList())
        .count(totalCount)
        .build();
  }

  @Override
  public void deleteById(Long strategyId) {
    Strategy strategy =
        strategyAccessService
            .findById(strategyId)
            .orElseThrow(() -> new EntityNotFoundException(Strategy.class, strategyId));
    if (!strategy.getTrades().isEmpty()
        || !strategy.getPositions().isEmpty()
        || !strategy.getDividends().isEmpty()) {
      throw new UnableToDeleteEntitiesException(
          "Strategy still assigned to trades, positions or dividends");
    }
    strategyAccessService.delete(strategy);
  }

  @Override
  public StrategyDetail findById(Long strategyId) {
    Strategy strategy =
        strategyAccessService
            .findById(strategyId)
            .orElseThrow(() -> new EntityNotFoundException(Strategy.class, strategyId));
    return strategyMapper.toStrategyDetailDto(strategy);
  }

  @Override
  public StrategyDetail updateName(NameUpdate nameUpdate) {
    Long strategyId = nameUpdate.getId();
    Strategy strategy =
        strategyAccessService
            .findById(strategyId)
            .orElseThrow(() -> new EntityNotFoundException(Strategy.class, strategyId));
    strategy.setName(nameUpdate.getName());
    return strategyMapper.toStrategyDetailDto(this.save(strategy));
  }

  @Override
  public StrategyDetail create(StrategyCreate strategyCreate) {
    long portfolioId = strategyCreate.getPortfolioId();
    Portfolio portfolio =
        portfolioAccessService
            .findById(portfolioId)
            .orElseThrow(() -> new EntityNotFoundException(Portfolio.class, portfolioId));
    Strategy createdStrategy =
        Strategy.builder()
            .name(strategyCreate.getName())
            .portfolio(portfolio)
            .accountId(portfolio.getAccountId())
            .build();
    return strategyMapper.toStrategyDetailDto(this.save(createdStrategy));
  }

  private Strategy save(Strategy strategy) {
    try {
      return strategyAccessService.save(strategy);
    } catch (Exception exc) {
      throw new UnableToSaveEntitiesException("Strategy cannot be saved. " + exc.getMessage());
    }
  }
}
