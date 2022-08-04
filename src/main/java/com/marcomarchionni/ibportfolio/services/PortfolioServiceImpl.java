package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.model.domain.Portfolio;
import com.marcomarchionni.ibportfolio.model.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PortfolioDetailDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.PortfolioListDto;
import com.marcomarchionni.ibportfolio.model.mapping.PortfolioMapper;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
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
    public List<PortfolioListDto> findAll() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        return portfolios.stream().map(portfolioMapper::toPortfolioListDto).collect(Collectors.toList());
    }

    @Override
    public PortfolioDetailDto findById(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Portfolio with id: " + id + " not found")
        );
        return portfolioMapper.toPortfolioDetailDto(portfolio);
    }

    @Override
    public void deleteById(Long id) {
        Portfolio portfolioToDelete = portfolioRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Portfolio with id: " + id + " not found")
        );
        if (portfolioToDelete.getStrategies().isEmpty()) {
            portfolioRepository.deleteById(id);
        } else {
            throw new UnableToDeleteEntitiesException("Portfolio contains strategies and cannot be deleted");
        }
    }

    @Override
    public PortfolioDetailDto create(PortfolioCreateDto portfolioCreateDto) {
        Portfolio portfolio = portfolioMapper.toEntity(portfolioCreateDto);
        return portfolioMapper.toPortfolioDetailDto(this.save(portfolio));
    }

    @Override
    public PortfolioDetailDto updateName(UpdateNameDto dto) {
        Portfolio portfolio = portfolioRepository.findById(dto.getId()).orElseThrow(
                ()->new EntityNotFoundException("Portfolio with id: " + dto.getId() + " not found.")
        );
        portfolio.setName(dto.getName());
        return portfolioMapper.toPortfolioDetailDto(this.save(portfolio));
    }

    private Portfolio save(Portfolio portfolio) {
        try {
            return portfolioRepository.save(portfolio);
        }
        catch (Exception exc) {
            throw new UnableToSaveEntitiesException(exc.getMessage());
        }
    }
}
