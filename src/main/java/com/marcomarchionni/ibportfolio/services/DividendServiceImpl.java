package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.Strategy;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
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
    public List<Dividend> findWithParameters(LocalDate fromExDate,
                                             LocalDate toExDate,
                                             LocalDate fromPayDate,
                                             LocalDate toPayDate,
                                             Boolean tagged,
                                             String symbol) {

        if (!StringUtils.hasText(symbol)) {
            symbol = null;
        }
        return dividendRepository.findWithParameters(fromExDate, toExDate, fromPayDate, toPayDate, tagged, symbol);
    }

    @Override
    public Dividend updateStrategyId(Dividend dividend) {

        Dividend dividendToUpdate = dividendRepository.findById(dividend.getId()).orElseThrow(
                ()-> new EntityNotFoundException("Dividend with id: " + dividend.getId() + " not found")
        );
        Strategy strategyToAssign = strategyRepository.findById(dividend.getStrategyId()).orElseThrow(
                ()-> new EntityNotFoundException("Strategy with id: " + dividend.getStrategyId() + " not found")
        );
        dividendToUpdate.setStrategyId(strategyToAssign.getId());
        return dividendRepository.save(dividendToUpdate);
    }
}
