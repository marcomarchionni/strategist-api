package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.accessservice.PortfolioAccessService;
import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioSummaryDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.PortfolioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioAccessService dataGateway;
    private final PortfolioMapper portfolioMapper;

    @Override
    public List<PortfolioSummaryDto> findAll() {
        List<Portfolio> portfolios = dataGateway.findAll();
        return portfolios.stream().map(portfolioMapper::toPortfolioListDto).collect(Collectors.toList());
    }

    @Override
    public PortfolioDetailDto findById(Long portfolioId) {
        Portfolio portfolio = dataGateway.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        return portfolioMapper.toPortfolioDetailDto(portfolio);
    }

    @Override
    public void deleteById(Long portfolioId) {
        Portfolio portfolioToDelete = dataGateway.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        if (!portfolioToDelete.getStrategies().isEmpty()) {
            throw new UnableToDeleteEntitiesException("Portfolio contains strategies and cannot be deleted");
        }
        dataGateway.delete(portfolioToDelete);
    }

    @Override
    @Transactional
    public PortfolioDetailDto create(PortfolioCreateDto portfolioCreateDto) {

        String portfolioName = portfolioCreateDto.getName();

        // Check if portfolio with the same name already exists
        boolean existsWithName = dataGateway.existsByName(portfolioName);
        if (existsWithName) {
            throw new UnableToSaveEntitiesException("Portfolio with name: " + portfolioCreateDto.getName() + " " +
                    "already exists");
        }

        // Create portfolio
        Portfolio savedPortfolio = dataGateway.save(portfolioMapper.toEntity(portfolioCreateDto));
        return portfolioMapper.toPortfolioDetailDto(savedPortfolio);
    }

    @Override
    @Transactional
    public PortfolioDetailDto updateName(UpdateNameDto dto) {

        // Get portfolio id
        Long portfolioId = dto.getId();

        // Check if the portfolio to update exists
        Portfolio portfolio = dataGateway.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        // Check if a portfolio with the same name already exists
        boolean existsWithName = dataGateway.existsByName(dto.getName());
        if (existsWithName) {
            throw new UnableToSaveEntitiesException("Portfolio with name: " + dto.getName() + " already exists.");
        }
        // Update portfolio name
        portfolio.setName(dto.getName());

        // Save portfolio
        Portfolio savedPortfolio = dataGateway.save(portfolio);
        return portfolioMapper.toPortfolioDetailDto(savedPortfolio);
    }
}
