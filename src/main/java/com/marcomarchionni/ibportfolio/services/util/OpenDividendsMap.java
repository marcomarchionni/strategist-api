package com.marcomarchionni.ibportfolio.services.util;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.mappers.DividendMapper;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;

import java.util.Map;
import java.util.stream.Collectors;

public class OpenDividendsMap {

    private final Map<Long, Dividend> openDividendsMap;

    private final DividendMapper mapper;

    private OpenDividendsMap(DividendMapper mapper, Map<Long, Dividend> openDividendsMap) {
        this.openDividendsMap = openDividendsMap;
        this.mapper = mapper;
    }

    public static OpenDividendsMap createOpenDividendMap(String accountId, DividendRepository dividendRepository,
                                                         DividendMapper mapper) {
        Map<Long, Dividend> openDividendsMap = dividendRepository.findOpenDividendsByAccountId(accountId).stream()
                .collect(Collectors.toMap(Dividend::getActionId, dividend -> dividend));
        return new OpenDividendsMap(mapper, openDividendsMap);
    }

    public boolean contains(Dividend dividend) {
        return openDividendsMap.containsKey(dividend.getActionId());
    }

    public Dividend getMergedDividend(Dividend dividend) {
        return mapper.mergeFlexProperties(dividend, openDividendsMap.get(dividend.getActionId()));
    }
}
