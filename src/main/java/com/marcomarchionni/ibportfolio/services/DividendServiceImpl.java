package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.DividendFindDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendSummaryDto;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.mappers.DividendMapper;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.services.util.OpenDividendsCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DividendServiceImpl implements DividendService {

    private final DividendRepository dividendRepository;
    private final StrategyRepository strategyRepository;
    private final DividendMapper mapper;

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
        return dividends.stream().map(mapper::toDividendListDto).collect(Collectors.toList());
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
        return mapper.toDividendListDto(this.save(user, dividend));
    }

    @Override
    public UpdateReport<Dividend> updateDividends(User user, List<Dividend> dividends) {

        if (dividends.isEmpty()) {
            return UpdateReport.<Dividend>builder().build();
        }

        // Retrieve authenticated user account id
        String accountId = user.getAccountId();

        // Init target lists
        List<Dividend> toAdd = new ArrayList<>();
        List<Dividend> toMerge = new ArrayList<>();
        List<Dividend> toSkip = new ArrayList<>();

        // Retrieve existing open dividends in an OpenDividendsMap instance
        List<Dividend> dbOpenDividends = dividendRepository.findOpenDividendsByAccountId(accountId);
        OpenDividendsCache dbCache = OpenDividendsCache.createOpenDividendCache(
                dbOpenDividends);

        // Assign dividends to target lists
        for (Dividend dividend : dividends) {
            if (dbCache.existMatch(dividend)) {
                var dbDividend = dbCache.getMatchingDividend(dividend);
                toMerge.add(mapper.mergeFlexProperties(dividend, dbDividend));
            } else if (!existsInDb(accountId, dividend)) {
                toAdd.add(dividend);
            } else {
                toSkip.add(dividend);
            }
        }

        // Save target lists and return report
        return UpdateReport.<Dividend>builder()
                .added(this.saveAll(user, toAdd))
                .merged(this.saveAll(user, toMerge))
                .skipped(toSkip).build();
    }

    private boolean existsInDb(String accountId, Dividend d) {
        return dividendRepository.existsByAccountIdAndActionId(accountId, d.getActionId());
    }

    private Dividend save(User user, Dividend dividend) {
        if (!dividend.getAccountId().equals(user.getAccountId())) {
            throw new InvalidUserDataException();
        }
        try {
            return dividendRepository.save(dividend);
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException(e.getMessage());
        }
    }

    private List<Dividend> saveAll(User user, List<Dividend> dividends) {
        if (!dividends.stream().allMatch(dividend -> dividend.getAccountId().equals(user.getAccountId()))) {
            throw new InvalidUserDataException();
        }
        try {
            return dividendRepository.saveAll(dividends);
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException(e.getMessage());
        }
    }
}
