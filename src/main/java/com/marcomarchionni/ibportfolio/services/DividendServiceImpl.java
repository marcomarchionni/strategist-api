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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public void updateDividends(List<Dividend> dividends) {

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
    public List<Dividend> saveOrIgnore(List<Dividend> closedDividends) {
        List<Dividend> dividendsToSave = new ArrayList<>();
        for (Dividend closedDividend : closedDividends) {
            Long id = closedDividend.getId();
            if (!dividendRepository.existsById(id)) {
                dividendsToSave.add(closedDividend);
            }
        }
        return dividendRepository.saveAll(dividendsToSave);
    }

    @Override
    public List<Dividend> updateDividends(List<Dividend> openDividends, List<Dividend> closedDividends) {
        List<Dividend> dividendsToSave = new ArrayList<>();

        // Create a map of open dividends
        Map<Long, Dividend> openDividendsMap = dividendRepository.findOpenDividends().stream()
                .collect(Collectors.toMap(Dividend::getId, dividend -> dividend));

        // select new open dividends to save
        for (Dividend openDividend : openDividends) {
            if (!openDividendsMap.containsKey(openDividend.getId())) {
                dividendsToSave.add(openDividend);
            }
        }

        // select new closed dividends to save, skip already saved
        for (Dividend closedDividend : closedDividends) {
            Long id = closedDividend.getId();
            if (openDividendsMap.containsKey(id)) {
                // Merge strategy from existing open dividend, so we don't overwrite it, then save closed dividend
                Strategy strategy = openDividendsMap.get(closedDividend.getId()).getStrategy();
                closedDividend.setStrategy(strategy);
                dividendsToSave.add(closedDividend);
            } else {
                if (!dividendRepository.existsById(id)) {
                    dividendsToSave.add(closedDividend);
                }
            }
        }

        return dividendRepository.saveAll(dividendsToSave);
    }

    @Override
    public DividendListDto updateStrategyId(UpdateStrategyDto dividendUpdate) {

        Dividend dividend = dividendRepository.findById(dividendUpdate.getId()).orElseThrow(
                () -> new EntityNotFoundException("Dividend with id: " + dividendUpdate.getId() + " not found")
        );
        Strategy strategyToAssign = strategyRepository.findById(dividendUpdate.getStrategyId()).orElseThrow(
                () -> new EntityNotFoundException("Strategy with id: " + dividendUpdate.getStrategyId() + " not found")
        );
        dividend.setStrategy(strategyToAssign);
        return dividendMapper.toDividendListDto(dividendRepository.save(dividend));
    }
}
