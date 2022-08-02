package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.models.domain.Portfolio;
import com.marcomarchionni.ibportfolio.models.dtos.request.PortfolioCreateDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateNameDto;
import com.marcomarchionni.ibportfolio.models.mapping.PortfolioMapperImpl;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    PortfolioRepository portfolioRepository;
    PortfolioMapperImpl portfolioMapper;

    @Autowired
    public PortfolioServiceImpl(PortfolioRepository portfolioRepository, PortfolioMapperImpl portfolioMapper) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioMapper = portfolioMapper;
    }

    @Override
    public List<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    @Override
    public Portfolio findById(Long id) {
        return portfolioRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Portfolio with id: " + id + " not found")
        );
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
    public Portfolio create(PortfolioCreateDto portfolioCreateDto) {
        Portfolio portfolio = portfolioMapper.toEntity(portfolioCreateDto);
        return this.save(portfolio);
    }

    @Override
    public Portfolio updateName(UpdateNameDto dto) {
        Portfolio targetPortfolio = portfolioRepository.findById(dto.getId()).orElseThrow(
                ()->new EntityNotFoundException("Portfolio with id: " + dto.getId() + " not found.")
        );
        targetPortfolio.setName(dto.getName());
        return this.save(targetPortfolio);
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
