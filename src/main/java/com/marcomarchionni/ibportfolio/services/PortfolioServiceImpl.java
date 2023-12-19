package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.dtos.response.PortfolioSummaryDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.PortfolioMapper;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<PortfolioSummaryDto> findAllByUser(User user) {
        String accountId = user.getAccountId();
        List<Portfolio> portfolios = portfolioRepository.findAllByAccountId(accountId);
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
    public PortfolioDetailDto create(User user, PortfolioCreateDto portfolioCreateDto) {
        // Get user account id
        String accountId = user.getAccountId();

        // Check if portfolio with the same name already exists
        boolean existsUserPortfolioWithTheSameName = portfolioRepository.existsByNameAndAccountId(accountId,
                portfolioCreateDto.getName());
        if (existsUserPortfolioWithTheSameName) {
            throw new UnableToSaveEntitiesException("Portfolio with name: " + portfolioCreateDto.getName() + " " +
                    "already exists for account: " + accountId);
        }
        // Create portfolio
        Portfolio savedPortfolio = portfolioRepository.save(portfolioMapper.toEntity(accountId, portfolioCreateDto));
        return portfolioMapper.toPortfolioDetailDto(savedPortfolio);
    }

    @Override
    @Transactional
    public PortfolioDetailDto updateName(User user, UpdateNameDto dto) {

        // Get user account id
        String accountId = user.getAccountId();

        // Check if the portfolio to update exists
        Portfolio portfolio = portfolioRepository.findById(dto.getId()).orElseThrow(
                () -> new EntityNotFoundException("Portfolio with id: " + dto.getId() + " not found.")
        );
        // Check if a portfolio with the same name already exists
        boolean existsPortfolioWithTheSameName = portfolioRepository.existsByNameAndAccountId(accountId, dto.getName());
        if (existsPortfolioWithTheSameName) {
            throw new UnableToSaveEntitiesException("Portfolio with name: " + dto.getName() + " already exists for " +
                    "account: " + accountId + ".");
        }
        // Update portfolio name
        portfolio.setName(dto.getName());

        // Save portfolio
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        return portfolioMapper.toPortfolioDetailDto(savedPortfolio);
    }
}
