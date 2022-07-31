package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.DividendFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DividendServiceImpl implements DividendService {

    private final DividendRepository dividendRepository;
    private final StrategyRepository strategyRepository;

    @Autowired
    DividendServiceImpl(DividendRepository dividendRepository, StrategyRepository strategyRepository) {
        this.dividendRepository = dividendRepository;
        this.strategyRepository = strategyRepository;
    }

    @Override
    public void saveDividends(List<Dividend> openOrClosedDividends) {
        dividendRepository.saveAll(openOrClosedDividends);
    }

    @Override
    public void deleteAllOpenDividends() {
        dividendRepository.deleteByOpenClosed("OPEN");
    }

    @Override
    public List<Dividend> findWithCriteria(DividendFindDto criteria) {
        return dividendRepository.findWithParameters(
                criteria.getExDateFrom(),
                criteria.getExDateTo(),
                criteria.getPayDateFrom(),
                criteria.getPayDateTo(),
                criteria.getTagged(),
                criteria.getSymbol());
    }

    @Override
    public Dividend updateStrategyId(UpdateStrategyDto dividendUpdate) {

        Dividend dividend = dividendRepository.findById(dividendUpdate.getId()).orElseThrow(
                ()-> new EntityNotFoundException("Dividend with id: " + dividendUpdate.getId() + " not found")
        );
        Strategy strategyToAssign = strategyRepository.findById(dividendUpdate.getStrategyId()).orElseThrow(
                ()-> new EntityNotFoundException("Strategy with id: " + dividendUpdate.getStrategyId() + " not found")
        );
        dividend.setStrategy(strategyToAssign);
        return dividendRepository.save(dividend);
    }
}
