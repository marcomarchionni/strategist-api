package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.models.Portfolio;
import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.StrategyFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateNameDto;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategyServiceImpl implements StrategyService {

    private final StrategyRepository strategyRepository;
    private final PortfolioRepository portfolioRepository;

    @Autowired
    public StrategyServiceImpl(StrategyRepository strategyRepository, PortfolioRepository portfolioRepository) {
        this.strategyRepository = strategyRepository;
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public List<Strategy> findByParams(StrategyFindDto strategyFind) {
        return strategyRepository.findByParams(strategyFind.getName());
    }

    @Override
    public void deleteById(Long id) {
        Strategy strategy = strategyRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Strategy with id: " + id + " not found")
        );
        if (strategy.getTrades().isEmpty() && strategy.getPositions().isEmpty() && strategy.getDividends().isEmpty()){
            strategyRepository.deleteById(id);
        }
        else {
            throw new UnableToDeleteEntitiesException("Strategy still assigned to trades, positions or dividends");
        }
    }

    @Override
    public Strategy updateName(UpdateNameDto updateNameDto) {
        Strategy strategy = strategyRepository.findById(updateNameDto.getId()).orElseThrow(
                ()-> new EntityNotFoundException("Strategy with id: " + updateNameDto.getId() + "not found")
        );
        strategy.setName(updateNameDto.getName());
        return this.save(strategy);
    }

    @Override
    public Strategy create(StrategyCreateDto strategyCreateDto) {
        Portfolio portfolio = portfolioRepository.findById(strategyCreateDto.getPortfolioId()).orElseThrow(
                ()-> new EntityNotFoundException("Portfolio with id: " + strategyCreateDto.getPortfolioId() + " not found")
        );
        Strategy strategy = new Strategy();
        strategy.setName(strategyCreateDto.getName());
        strategy.setPortfolio(portfolio);
        return this.save(strategy);
    }

    private Strategy save(Strategy strategy) {
        try {
            return strategyRepository.save(strategy);
        } catch (Exception exc) {
            throw new UnableToSaveEntitiesException("Strategy cannot be saved." + exc.getMessage());
        }
    }
}
