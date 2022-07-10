package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;

public interface StrategyService {

    boolean strategyDoesNotExist(Long strategyId);
}
