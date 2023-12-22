package com.marcomarchionni.ibportfolio.services.util;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.mappers.DividendMapper;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;

import java.util.Map;
import java.util.stream.Collectors;

public class OpenDividendsUpdateManager {

    private final Map<Long, Dividend> openDividendsMap;

    private final DividendMapper mapper;

    private OpenDividendsUpdateManager(DividendMapper mapper, Map<Long, Dividend> openDividendsMap) {
        this.openDividendsMap = openDividendsMap;
        this.mapper = mapper;
    }

    public static OpenDividendsUpdateManager createOpenDividendMap(String accountId,
                                                                   DividendRepository dividendRepository,
                                                                   DividendMapper mapper) {
        Map<Long, Dividend> openDividendsMap = dividendRepository.findOpenDividendsByAccountId(accountId)
                .stream()
                .collect(Collectors.toMap(Dividend::getActionId, dividend -> dividend));
        return new OpenDividendsUpdateManager(mapper, openDividendsMap);
    }

    public boolean contains(Dividend dividend) {
        return openDividendsMap.containsKey(dividend.getActionId());
    }

    public Dividend getMergedDividend(Dividend dividend) {
        return mapper.mergeFlexProperties(dividend, openDividendsMap.get(dividend.getActionId()));
    }
}
