package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.models.domain.Portfolio;
import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.StrategyFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyDetailDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.StrategyListDto;
import com.marcomarchionni.ibportfolio.models.mapping.StrategyMapper;
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
    public StrategyServiceImpl(StrategyRepository strategyRepository, PortfolioRepository portfolioRepository, StrategyMapper strategyMapper) {
        this.strategyRepository = strategyRepository;
        this.portfolioRepository = portfolioRepository;
        this.strategyMapper = strategyMapper;
    }

    @Override
    public List<StrategyListDto> findByParams(StrategyFindDto strategyFind) {
        List<Strategy> strategies = strategyRepository.findByParams(strategyFind.getName());
        return strategies.stream().map(strategyMapper::toStrategyListDto).collect(Collectors.toList());
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
    public StrategyDetailDto findById(Long id) {
        Strategy strategy = strategyRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Strategy with id: " + id + " not found")
        );
        return strategyMapper.toStrategyDetailDto(strategy);
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
        Strategy strategy = strategyMapper.toEntity(strategyCreateDto);
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
