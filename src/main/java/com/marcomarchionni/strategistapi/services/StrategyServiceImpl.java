package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.PortfolioAccessService;
import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFind;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetail;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.strategistapi.mappers.StrategyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StrategyServiceImpl implements StrategyService {
    private final StrategyAccessService strategyAccessService;
    private final PortfolioAccessService portfolioAccessService;
    private final StrategyMapper strategyMapper;

    @Override
    public List<StrategySummary> findByFilter(StrategyFind strategyFind) {
        List<Strategy> strategies = strategyAccessService.findByParams(strategyFind.getName());
        return strategies.stream().map(strategyMapper::toStrategySummaryDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long strategyId) {
        Strategy strategy = strategyAccessService.findById(strategyId).orElseThrow(
                () -> new EntityNotFoundException(Strategy.class, strategyId)
        );
        if (!strategy.getTrades().isEmpty() || !strategy.getPositions().isEmpty() || !strategy.getDividends()
                .isEmpty()) {
            throw new UnableToDeleteEntitiesException("Strategy still assigned to trades, positions or dividends");
        }
        strategyAccessService.delete(strategy);
    }

    @Override
    public StrategyDetail findById(Long strategyId) {
        Strategy strategy = strategyAccessService.findById(strategyId).orElseThrow(
                () -> new EntityNotFoundException(Strategy.class, strategyId)
        );
        return strategyMapper.toStrategyDetailDto(strategy);
    }

    @Override
    public StrategyDetail updateName(NameUpdate nameUpdate) {
        Long strategyId = nameUpdate.getId();
        Strategy strategy = strategyAccessService.findById(strategyId).orElseThrow(
                () -> new EntityNotFoundException(Strategy.class, strategyId)
        );
        strategy.setName(nameUpdate.getName());
        return strategyMapper.toStrategyDetailDto(this.save(strategy));
    }

    @Override
    public StrategyDetail create(StrategyCreate strategyCreate) {
        long portfolioId = strategyCreate.getPortfolioId();
        Portfolio portfolio = portfolioAccessService.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        Strategy createdStrategy = Strategy.builder().name(strategyCreate.getName()).portfolio(portfolio)
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
