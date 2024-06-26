package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.DividendAccessService;
import com.marcomarchionni.strategistapi.accessservice.StrategyAccessService;
import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.dtos.request.DividendFind;
import com.marcomarchionni.strategistapi.dtos.request.StrategyAssign;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummary;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateReport;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.strategistapi.mappers.DividendMapper;
import com.marcomarchionni.strategistapi.services.util.OpenDividendsCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DividendServiceImpl implements DividendService {

    private final DividendAccessService dividendAccessService;
    private final StrategyAccessService strategyAccessService;
    private final DividendMapper mapper;

    @Override
    public List<DividendSummary> findByFilter(DividendFind dividendFind) {
        List<Dividend> dividends = dividendAccessService.findByParams(
                dividendFind.getExDateAfter(),
                dividendFind.getExDateBefore(),
                dividendFind.getPayDateAfter(),
                dividendFind.getPayDateBefore(),
                dividendFind.getTagged(),
                dividendFind.getSymbol()
        );
        return dividends.stream().map(mapper::toDividendSummary).collect(Collectors.toList());
    }

    @Override
    public DividendSummary updateStrategyId(StrategyAssign dividendUpdate) {
        Long dividendId = dividendUpdate.getId();
        Long strategyId = dividendUpdate.getStrategyId();

        Dividend dividend = dividendAccessService.findById(dividendId).orElseThrow(
                () -> new EntityNotFoundException(Dividend.class, dividendId)
        );

        Strategy strategyToAssign = Optional.ofNullable(strategyId)
                .map(id -> strategyAccessService.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(Strategy.class, id)
                )).orElse(null);

        dividend.setStrategy(strategyToAssign);
        return mapper.toDividendSummary(this.save(dividend));
    }

    @Override
    public UpdateReport<DividendSummary> updateDividends(List<Dividend> dividends) {

        if (dividends.isEmpty()) {
            return UpdateReport.<DividendSummary>builder().build();
        }

        // Init target lists
        List<Dividend> toAdd = new ArrayList<>();
        List<Dividend> toMerge = new ArrayList<>();
        List<Dividend> skipped = new ArrayList<>();

        // Retrieve existing open dividends in an OpenDividendsMap instance
        List<Dividend> dbOpenDividends = dividendAccessService.findOpenDividends();
        OpenDividendsCache dbCache = OpenDividendsCache.createOpenDividendCache(
                dbOpenDividends);

        // Assign dividends to target lists
        for (Dividend dividend : dividends) {
            if (dbCache.existMatch(dividend)) {
                var dbDividend = dbCache.getMatchingDividend(dividend);
                toMerge.add(mapper.mergeFlexProperties(dividend, dbDividend));
            } else if (!this.existsInDb(dividend)) {
                toAdd.add(dividend);
            } else {
                skipped.add(dividend);
            }
        }

        // Save target lists and return report
        return createUpdateReport(
                this.saveAll(toAdd),
                this.saveAll(toMerge),
                skipped);
    }

    private boolean existsInDb(Dividend d) {
        return dividendAccessService.existsByActionId(d.getActionId());
    }

    private Dividend save(Dividend dividend) {
        try {
            return dividendAccessService.save(dividend);
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException(e.getMessage());
        }
    }

    private List<Dividend> saveAll(List<Dividend> dividends) {
        try {
            return dividendAccessService.saveAll(dividends);
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException(e.getMessage());
        }
    }

    private UpdateReport<DividendSummary> createUpdateReport(List<Dividend> added, List<Dividend> merged,
                                                             List<Dividend> skipped) {
        return UpdateReport.<DividendSummary>builder()
                .added(added.stream().map(mapper::toDividendSummary).toList())
                .merged(merged.stream().map(mapper::toDividendSummary).toList())
                .skipped(skipped.stream().map(mapper::toDividendSummary).toList())
                .build();
    }
}
