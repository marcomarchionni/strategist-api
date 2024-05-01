package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.PortfolioAccessService;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.strategistapi.mappers.PortfolioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioAccessService portfolioAccessService;
    private final UserService userService;
    private final PortfolioMapper portfolioMapper;


    @Override
    public List<PortfolioSummary> findAll() {
        List<Portfolio> portfolios = portfolioAccessService.findAll();
        return portfolios.stream().map(portfolioMapper::portfolioToPortfolioSummary).collect(Collectors.toList());
    }

    @Override
    public PortfolioDetail findById(Long portfolioId) {
        Portfolio portfolio = portfolioAccessService.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        return portfolioMapper.toPortfolioDetailDto(portfolio);
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.PORTFOLIO;
    }

    @Override
    public void deleteById(Long portfolioId) {
        Portfolio portfolioToDelete = portfolioAccessService.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        if (!portfolioToDelete.getStrategies().isEmpty()) {
            throw new UnableToDeleteEntitiesException("Portfolio contains strategies and cannot be deleted");
        }
        portfolioAccessService.delete(portfolioToDelete);
    }


    @Override
    @Transactional
    public PortfolioSummary create(PortfolioSave portfolioSave) {

        // Check if portfolio with the same name already exists
        checkIfPortfolioNameExists(portfolioSave.getName());

        // Save portfolio
        var portfolio = Portfolio.builder().accountId(userService.getUserAccountId()).build();
        return mergeAndSave(portfolioSave, portfolio);
    }

    @Override
    @Transactional
    public PortfolioSummary update(PortfolioSave portfolioDto) {

        // Get portfolio id
        Long portfolioId = portfolioDto.getId();

        // Check if the portfolio to update exists
        Portfolio portfolio = portfolioAccessService.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        // Check if a portfolio with the same name already exists
        checkIfPortfolioNameExists(portfolioDto.getName());

        return mergeAndSave(portfolioDto, portfolio);
    }

    private void checkIfPortfolioNameExists(String name) {
        if (portfolioAccessService.existsByName(name)) {
            throw new UnableToSaveEntitiesException("Portfolio with name: " + name + " already exists.");
        }
    }

    private PortfolioSummary mergeAndSave(PortfolioSave dto, Portfolio portfolio) {
        portfolioMapper.mergePortfolioSaveToPortfolio(dto, portfolio);
        Portfolio savedPortfolio = portfolioAccessService.save(portfolio);
        return portfolioMapper.portfolioToPortfolioSummary(savedPortfolio);
    }
}
