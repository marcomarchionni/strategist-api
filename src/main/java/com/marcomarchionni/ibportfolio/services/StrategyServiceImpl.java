package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.StrategySummaryDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.StrategyMapper;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StrategyServiceImpl implements StrategyService {

    private final StrategyRepository strategyRepository;
    private final PortfolioRepository portfolioRepository;
    private final StrategyMapper strategyMapper;

    @Autowired
    public StrategyServiceImpl(StrategyRepository strategyRepository, PortfolioRepository portfolioRepository,
                               StrategyMapper strategyMapper) {
        this.strategyRepository = strategyRepository;
        this.portfolioRepository = portfolioRepository;
        this.strategyMapper = strategyMapper;
    }

    @Override
    public List<StrategySummaryDto> findByFilter(User user, StrategyFindDto strategyFind) {
        String accountId = user.getAccountId();
        List<Strategy> strategies = strategyRepository.findByParams(accountId, strategyFind.getName());
        return strategies.stream().map(strategyMapper::toStrategySummaryDto).collect(Collectors.toList());
    }

    @Override
    public void deleteByUserAndId(User user, Long strategyId) {
        String accountId = user.getAccountId();
        Strategy strategy = strategyRepository.findByIdAndAccountId(strategyId, accountId).orElseThrow(
                () -> new EntityNotFoundException(Strategy.class, strategyId, accountId)
        );
        if (strategy.getTrades().isEmpty() && strategy.getPositions().isEmpty() && strategy.getDividends().isEmpty()) {
            strategyRepository.deleteById(strategyId);
        } else {
            throw new UnableToDeleteEntitiesException("Strategy still assigned to trades, positions or dividends");
        }
    }

    @Override
    public StrategyDetailDto findByUserAndId(User user, Long strategyId) {
        String accountId = user.getAccountId();
        Strategy strategy = strategyRepository.findByIdAndAccountId(strategyId, accountId).orElseThrow(
                () -> new EntityNotFoundException(Strategy.class, strategyId, accountId)
        );
        return strategyMapper.toStrategyDetailDto(strategy);
    }

    @Override
    public StrategyDetailDto updateName(User user, UpdateNameDto updateNameDto) {
        Long strategyId = updateNameDto.getId();
        String accountId = user.getAccountId();
        Strategy strategy = strategyRepository.findByIdAndAccountId(strategyId, accountId).orElseThrow(
                () -> new EntityNotFoundException(Strategy.class, strategyId, accountId)
        );
        strategy.setName(updateNameDto.getName());
        return strategyMapper.toStrategyDetailDto(this.save(strategy));
    }

    @Override
    public StrategyDetailDto create(User user, StrategyCreateDto strategyCreateDto) {
        String accountId = user.getAccountId();
        long portfolioId = strategyCreateDto.getPortfolioId();
        Portfolio portfolio = portfolioRepository.findByIdAndAccountId(portfolioId, accountId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId, accountId)
        );
        Strategy createdStrategy = Strategy.builder().name(strategyCreateDto.getName()).portfolio(portfolio)
                .accountId(accountId).build();
        return strategyMapper.toStrategyDetailDto(this.save(createdStrategy));
    }

    private Strategy save(Strategy strategy) {
        try {
            return strategyRepository.save(strategy);
        } catch (Exception exc) {
            throw new UnableToSaveEntitiesException("Strategy cannot be saved." + exc.getMessage());
        }
    }
}
