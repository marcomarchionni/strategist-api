package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendListDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.DividendMapper;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DividendServiceImpl implements DividendService {

    private final DividendRepository dividendRepository;
    private final StrategyRepository strategyRepository;
    private final DividendMapper dividendMapper;

    @Autowired
    DividendServiceImpl(DividendRepository dividendRepository, StrategyRepository strategyRepository,
                        DividendMapper dividendMapper) {
        this.dividendRepository = dividendRepository;
        this.strategyRepository = strategyRepository;
        this.dividendMapper = dividendMapper;
    }

    @Override
    public void saveDividends(List<Dividend> openOrClosedDividends) {
        dividendRepository.saveAll(openOrClosedDividends);
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
    public UpdateReport<Dividend> addOrSkip(List<Dividend> closedDividends) {
        // Init lists
        List<Dividend> dividendsToAdd = new ArrayList<>();
        List<Dividend> dividendsToSkip = new ArrayList<>();

        // Check if dividend is already in the database
        for (Dividend closedDividend : closedDividends) {
            Long id = closedDividend.getId();
            if (dividendRepository.existsById(id)) {
                dividendsToSkip.add(closedDividend);
            } else {
                dividendsToAdd.add(closedDividend);
            }
        }
        return UpdateReport.<Dividend>builder().added(dividendRepository.saveAll(dividendsToAdd))
                .skipped(dividendsToSkip).build();
    }

    @Override
    public UpdateReport<Dividend> updateDividends(List<Dividend> openDividends, List<Dividend> closedDividends) {
        // Create a map of existing open dividends
        Map<Long, Dividend> existingOpenDividendsMap = dividendRepository.findOpenDividends().stream()
                .collect(Collectors.toMap(Dividend::getId, dividend -> dividend));

        // Select new open dividends to add (not yet present in the database)
        List<Dividend> openDividendsToAdd = openDividends.stream()
                .filter(openDividend -> !existingOpenDividendsMap.containsKey(openDividend.getId()))
                .toList();

        // Select new closed dividends to add (not yet present in the database)
        List<Dividend> closedDividendsToAdd = closedDividends.stream()
                .filter(closedDividend -> !dividendRepository.existsById(closedDividend.getId())).toList();

        // Select dividends to add (new open and closed dividends)
        List<Dividend> dividendsToAdd = Stream.concat(openDividendsToAdd.stream(), closedDividendsToAdd.stream())
                .toList();

        // Select new closed dividends to merge (these are dividends that were open and now are paid out)
        List<Dividend> closedDividendsToMerge = closedDividends.stream()
                .filter(closedDividend -> existingOpenDividendsMap.containsKey(closedDividend.getId()))
                .map(closedDividend -> copyAllPropertiesButStrategy(closedDividend,
                        existingOpenDividendsMap.get(closedDividend.getId())))
                .toList();

        // Select new open dividends to merge
        // (some value may change before pay date, so it's safer to update open dividends to the latest values)
        List<Dividend> openDividendToMerge = openDividends.stream()
                .filter(openDividend -> existingOpenDividendsMap.containsKey(openDividend.getId()))
                .map(openDividend -> copyAllPropertiesButStrategy(openDividend,
                        existingOpenDividendsMap.get(openDividend.getId())))
                .toList();

        // Select dividends to merge (new open and closed dividends)
        List<Dividend> dividendsToMerge = Stream.concat(openDividendToMerge.stream(), closedDividendsToMerge.stream())
                .toList();

        // Select new closed dividends to skip (these are already in the database, there is no need to update them)
        List<Dividend> closedDividendToSkip = closedDividends.stream()
                .filter(closedDividend -> !existingOpenDividendsMap.containsKey(closedDividend.getId()))
                .filter(closedDividend -> dividendRepository.existsById(closedDividend.getId())).toList();

        return UpdateReport.<Dividend>builder().added(saveAll(dividendsToAdd)).merged(saveAll(dividendsToMerge))
                .skipped(closedDividendToSkip).build();
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

    private Dividend copyAllPropertiesButStrategy(Dividend source, Dividend target) {
        target.setConId(source.getConId());
        target.setSymbol(source.getSymbol());
        target.setDescription(source.getDescription());
        target.setExDate(source.getExDate());
        target.setPayDate(source.getPayDate());
        target.setGrossRate(source.getGrossRate());
        target.setQuantity(source.getQuantity());
        target.setGrossAmount(source.getGrossAmount());
        target.setTax(source.getTax());
        target.setNetAmount(source.getNetAmount());
        target.setOpenClosed(source.getOpenClosed());
        return target;
    }

    private List<Dividend> saveAll(List<Dividend> dividends) {
        try {
            return dividendRepository.saveAll(dividends);
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException(e.getMessage());
        }
    }
}
