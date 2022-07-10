package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StrategyServiceImpl implements StrategyService {

    private final StrategyRepository strategyRepository;

    @Autowired
    public StrategyServiceImpl(StrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    @Override
    public boolean strategyDoesNotExist(Long strategyId) {

        return strategyRepository.findById(strategyId).isEmpty();
    }
}
