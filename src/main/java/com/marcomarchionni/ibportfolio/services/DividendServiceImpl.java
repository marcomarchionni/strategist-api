package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidDataException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.DividendMapper;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.services.util.OpenDividendsUpdateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DividendServiceImpl implements DividendService {

    private final DividendRepository dividendRepository;
    private final StrategyRepository strategyRepository;
    private final DividendMapper dividendMapper;

    @Override
    public List<DividendSummaryDto> findByFilter(User user, DividendFindDto dividendFind) {
        List<Dividend> dividends = dividendRepository.findByParams(
                user.getAccountId(),
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
    public DividendSummaryDto updateStrategyId(User user, UpdateStrategyDto dividendUpdate) {
        Long dividendId = dividendUpdate.getId();
        Long strategyId = dividendUpdate.getStrategyId();
        String accountId = user.getAccountId();

        Dividend dividend = dividendRepository.findByIdAndAccountId(dividendId, accountId).orElseThrow(
                () -> new EntityNotFoundException(Dividend.class, dividendId, accountId)
        );
        Strategy strategyToAssign = strategyRepository.findByIdAndAccountId(strategyId, accountId).orElseThrow(
                () -> new EntityNotFoundException(Strategy.class, strategyId, accountId)
        );
        dividend.setStrategy(strategyToAssign);
        return dividendMapper.toDividendListDto(this.save(user, dividend));
    }

    @Override
    public UpdateReport<Dividend> addOrSkip(User user, List<Dividend> closedDividends) {
        // Retrieve authenticated user account id
        String accountId = user.getAccountId();

        // Init lists
        List<Dividend> dividendsToAdd = new ArrayList<>();
        List<Dividend> dividendsToSkip = new ArrayList<>();

        // Only add dividends that are not already in the database
        for (Dividend cd : closedDividends) {
            if (existsInDb(accountId, cd)) {
                dividendsToSkip.add(cd);
            } else {
                dividendsToAdd.add(cd);
            }
        }
        return UpdateReport.<Dividend>builder()
                .added(this.saveAll(user, dividendsToAdd))
                .skipped(dividendsToSkip).build();
    }

    @Override
    public UpdateReport<Dividend> updateDividends(User user, List<Dividend> openDividends,
                                                  List<Dividend> closedDividends) {
        // Retrieve authenticated user account id
        String accountId = user.getAccountId();

        // Combine open dividends and closed dividends into a single list
        List<Dividend> dividends = Stream.concat(openDividends.stream(), closedDividends.stream())
                .toList();

        // Init target lists
        List<Dividend> newDividendsToSave = new ArrayList<>();
        List<Dividend> mergedDividendsToSave = new ArrayList<>();
        List<Dividend> dividendToSkip = new ArrayList<>();

        // Retrieve existing open dividends in an OpenDividendsMap instance
        OpenDividendsUpdateManager openDividendsMap = OpenDividendsUpdateManager.createOpenDividendMap(accountId,
                dividendRepository,
                dividendMapper);

        // Assign dividends to target lists
        for (Dividend d : dividends) {
            if (openDividendsMap.contains(d)) {
                mergedDividendsToSave.add(openDividendsMap.getMergedDividend(d));
            } else if (!existsInDb(accountId, d)) {
                newDividendsToSave.add(d);
            } else {
                dividendToSkip.add(d);
            }
        }

        // Save target lists and return report
        return UpdateReport.<Dividend>builder()
                .added(this.saveAll(user, newDividendsToSave))
                .merged(this.saveAll(user, mergedDividendsToSave))
                .skipped(dividendToSkip).build();
    }

    private boolean existsInDb(String accountId, Dividend d) {
        return dividendRepository.existsByAccountIdAndActionId(accountId, d.getActionId());
    }

    private Dividend save(User user, Dividend dividend) {
        if (!dividend.getAccountId().equals(user.getAccountId())) {
            throw new InvalidDataException();
        }
        try {
            return dividendRepository.save(dividend);
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException(e.getMessage());
        }
    }

    private List<Dividend> saveAll(User user, List<Dividend> dividends) {
        if (!dividends.stream().allMatch(dividend -> dividend.getAccountId().equals(user.getAccountId()))) {
            throw new InvalidDataException();
        }
        try {
            return dividendRepository.saveAll(dividends);
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException(e.getMessage());
        }
    }
}
