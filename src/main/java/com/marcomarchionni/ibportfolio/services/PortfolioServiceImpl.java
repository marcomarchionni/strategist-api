package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioSummaryDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.PortfolioMapper;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    PortfolioRepository portfolioRepository;
    PortfolioMapper portfolioMapper;

    @Autowired
    public PortfolioServiceImpl(PortfolioRepository portfolioRepository, PortfolioMapper portfolioMapper) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioMapper = portfolioMapper;
    }

    @Override
    public List<PortfolioSummaryDto> findAll() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        return portfolios.stream().map(portfolioMapper::toPortfolioListDto).collect(Collectors.toList());
    }

    @Override
    public PortfolioDetailDto findById(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Portfolio with id: " + id + " not found")
        );
        return portfolioMapper.toPortfolioDetailDto(portfolio);
    }

    @Override
    public void deleteById(Long id) {
        Portfolio portfolioToDelete = portfolioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Portfolio with id: " + id + " not found")
        );
        if (portfolioToDelete.getStrategies().isEmpty()) {
            portfolioRepository.deleteById(id);
        } else {
            throw new UnableToDeleteEntitiesException("Portfolio contains strategies and cannot be deleted");
        }
    }

    @Override
    public PortfolioDetailDto create(@Valid PortfolioCreateDto portfolioCreateDto) {
        // Check if portfolio with the same name already exists
        boolean existsPortfolioWithTheSameName = portfolioRepository.existsByName(portfolioCreateDto.getName());
        if (existsPortfolioWithTheSameName) {
            throw new UnableToSaveEntitiesException("Portfolio with name: " + portfolioCreateDto.getName() + " " +
                    "already exists");
        }
        // Create portfolio
        Portfolio savedPortfolio = portfolioRepository.save(portfolioMapper.toEntity(portfolioCreateDto));
        return portfolioMapper.toPortfolioDetailDto(savedPortfolio);
    }

    @Override
    public PortfolioDetailDto updateName(@Valid UpdateNameDto dto) {
        // Check if the portfolio to update exists
        Portfolio portfolio = portfolioRepository.findById(dto.getId()).orElseThrow(
                () -> new EntityNotFoundException("Portfolio with id: " + dto.getId() + " not found.")
        );
        // Check if a portfolio with the same name already exists
        boolean existsPortfolioWithTheSameName = portfolioRepository.existsByName(dto.getName());
        if (existsPortfolioWithTheSameName) {
            throw new UnableToSaveEntitiesException("Portfolio with name: " + dto.getName() + " already exists");
        }
        // Update portfolio name
        portfolio.setName(dto.getName());

        // Save portfolio
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        return portfolioMapper.toPortfolioDetailDto(savedPortfolio);
    }
}
