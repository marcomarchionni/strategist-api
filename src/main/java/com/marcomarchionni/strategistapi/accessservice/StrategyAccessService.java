package com.marcomarchionni.strategistapi.accessservice;

import com.marcomarchionni.strategistapi.domain.Strategy;

import java.util.List;
import java.util.Optional;

public interface StrategyAccessService {
    List<Strategy> findByParams(String name);

    Optional<Strategy> findById(Long id);

    Strategy save(Strategy strategy);

    void delete(Strategy strategy);
}
