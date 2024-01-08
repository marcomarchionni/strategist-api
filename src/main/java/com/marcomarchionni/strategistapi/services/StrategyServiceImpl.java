package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.PortfolioAccessService;
import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreateDto;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFindDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateNameDto;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetailDto;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummaryDto;
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
    public List<StrategySummaryDto> findByFilter(StrategyFindDto strategyFind) {
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
    public StrategyDetailDto findById(Long strategyId) {
        Strategy strategy = strategyAccessService.findById(strategyId).orElseThrow(
                () -> new EntityNotFoundException(Strategy.class, strategyId)
        );
        return strategyMapper.toStrategyDetailDto(strategy);
    }

    @Override
    public StrategyDetailDto updateName(UpdateNameDto updateNameDto) {
        Long strategyId = updateNameDto.getId();
        Strategy strategy = strategyAccessService.findById(strategyId).orElseThrow(
                () -> new EntityNotFoundException(Strategy.class, strategyId)
        );
        strategy.setName(updateNameDto.getName());
        return strategyMapper.toStrategyDetailDto(this.save(strategy));
    }

    @Override
    public StrategyDetailDto create(StrategyCreateDto strategyCreateDto) {
        long portfolioId = strategyCreateDto.getPortfolioId();
        Portfolio portfolio = portfolioAccessService.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        Strategy createdStrategy = Strategy.builder().name(strategyCreateDto.getName()).portfolio(portfolio)
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
