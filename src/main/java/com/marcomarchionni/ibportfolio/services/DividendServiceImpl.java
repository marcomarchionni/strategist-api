package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.models.domain.Dividend;
import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import com.marcomarchionni.ibportfolio.models.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.DividendListDto;
import com.marcomarchionni.ibportfolio.models.mapping.DividendMapper;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DividendServiceImpl implements DividendService {

    private final DividendRepository dividendRepository;
    private final StrategyRepository strategyRepository;
    private final DividendMapper dividendMapper;

    @Autowired
    DividendServiceImpl(DividendRepository dividendRepository, StrategyRepository strategyRepository, DividendMapper dividendMapper) {
        this.dividendRepository = dividendRepository;
        this.strategyRepository = strategyRepository;
        this.dividendMapper = dividendMapper;
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
    public List<DividendListDto> findByParams(DividendFindDto criteria) {
        List<Dividend> dividends = dividendRepository.findWithParameters(
                criteria.getExDateFrom(),
                criteria.getExDateTo(),
                criteria.getPayDateFrom(),
                criteria.getPayDateTo(),
                criteria.getTagged(),
                criteria.getSymbol()
        );
        return dividends.stream().map(dividendMapper::toDividendListDto).collect(Collectors.toList());
    }

    @Override
    public DividendListDto updateStrategyId(UpdateStrategyDto dividendUpdate) {

        Dividend dividend = dividendRepository.findById(dividendUpdate.getId()).orElseThrow(
                ()-> new EntityNotFoundException("Dividend with id: " + dividendUpdate.getId() + " not found")
        );
        Strategy strategyToAssign = strategyRepository.findById(dividendUpdate.getStrategyId()).orElseThrow(
                ()-> new EntityNotFoundException("Strategy with id: " + dividendUpdate.getStrategyId() + " not found")
        );
        dividend.setStrategy(strategyToAssign);
        return dividendMapper.toDividendListDto(dividendRepository.save(dividend));
    }
}
