package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.PortfolioAccessService;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioCreate;
import com.marcomarchionni.strategistapi.dtos.request.UpdateName;
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
        return portfolios.stream().map(portfolioMapper::toPortfolioSummaryDto).collect(Collectors.toList());
    }

    @Override
    public PortfolioDetail findById(Long portfolioId) {
        Portfolio portfolio = portfolioAccessService.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        return portfolioMapper.toPortfolioDetailDto(portfolio);
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
    public PortfolioDetail create(PortfolioCreate portfolioCreate) {

        String portfolioName = portfolioCreate.getName();

        // Check if portfolio with the same name already exists
        boolean existsWithName = portfolioAccessService.existsByName(portfolioName);
        if (existsWithName) {
            throw new UnableToSaveEntitiesException("Portfolio with name: " + portfolioCreate.getName() + " " +
                    "already exists");
        }

        // Create portfolio
        Portfolio portfolioToSave = Portfolio.builder()
                .name(portfolioName)
                .accountId(userService.getUserAccountId())
                .build();

        // Save portfolio
        Portfolio savedPortfolio = portfolioAccessService.save(portfolioToSave);
        return portfolioMapper.toPortfolioDetailDto(savedPortfolio);
    }

    @Override
    @Transactional
    public PortfolioDetail updateName(UpdateName dto) {

        // Get portfolio id
        Long portfolioId = dto.getId();

        // Check if the portfolio to update exists
        Portfolio portfolio = portfolioAccessService.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        // Check if a portfolio with the same name already exists
        boolean existsWithName = portfolioAccessService.existsByName(dto.getName());
        if (existsWithName) {
            throw new UnableToSaveEntitiesException("Portfolio with name: " + dto.getName() + " already exists.");
        }
        // Update portfolio name
        portfolio.setName(dto.getName());

        // Save portfolio
        Portfolio savedPortfolio = portfolioAccessService.save(portfolio);
        return portfolioMapper.toPortfolioDetailDto(savedPortfolio);
    }
}
