package com.marcomarchionni.strategistapi.services.util;

import com.marcomarchionni.strategistapi.domain.Dividend;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OpenDividendsCache {

    private final Map<Long, Dividend> openDividendsMap;

    public static OpenDividendsCache createOpenDividendCache(List<Dividend> openDividends) {
        Map<Long, Dividend> openDividendsMap = openDividends
                .stream()
                .collect(Collectors.toMap(Dividend::getActionId, dividend -> dividend));
        return new OpenDividendsCache(openDividendsMap);
    }

    public boolean existMatch(Dividend dividend) {
        return openDividendsMap.containsKey(dividend.getActionId());
    }

    public Dividend getMatchingDividend(Dividend dividend) {
        return openDividendsMap.get(dividend.getActionId());
    }
}
