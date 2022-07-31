package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.models.Portfolio;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateNameDto;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
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
    public Portfolio save(Portfolio portfolio) {
        try {
            return portfolioRepository.save(portfolio);
        }
        catch (Exception exc) {
            throw new UnableToSaveEntitiesException(exc.getMessage());
        }
    }

    @Override
    public Portfolio updateName(UpdateNameDto dto) {
        Portfolio targetPortfolio = portfolioRepository.findById(dto.getId()).orElseThrow(
                ()->new EntityNotFoundException("Portfolio with id: " + dto.getId() + " not found.")
        );
        targetPortfolio.setName(dto.getName());
        return this.save(targetPortfolio);
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
}
