package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.Strategy;
import com.marcomarchionni.ibportfolio.model.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.model.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.model.dtos.response.DividendListDto;
import com.marcomarchionni.ibportfolio.model.mapping.DividendMapper;
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
    public List<DividendListDto> findByFilter(DividendFindDto dividendFind) {
        List<Dividend> dividends = dividendRepository.findByParams(
                dividendFind.getExDateFrom(),
                dividendFind.getExDateTo(),
                dividendFind.getPayDateFrom(),
                dividendFind.getPayDateTo(),
                dividendFind.getTagged(),
                dividendFind.getSymbol()
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
