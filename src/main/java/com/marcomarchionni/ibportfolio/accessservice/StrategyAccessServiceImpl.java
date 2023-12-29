package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.ibportfolio.repositories.StrategyRepository;
import com.marcomarchionni.ibportfolio.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StrategyAccessServiceImpl implements StrategyAccessService {

    private final UserService userService;
    private final StrategyRepository strategyRepository;

    @Override
    public List<Strategy> findByParams(String name) {
        String accountId = userService.getUserAccountId();
        return strategyRepository.findByParams(accountId, name);
    }

    @Override
    public Optional<Strategy> findById(Long id) {
        String accountId = userService.getUserAccountId();
        return strategyRepository.findByIdAndAccountId(id, accountId);
    }

    @Override
    public Strategy save(Strategy strategy) {
        String userAccountId = userService.getUserAccountId();
        if (strategy.getAccountId() != null && !strategy.getAccountId().equals(userAccountId)) {
            throw new InvalidUserDataException("Strategy with id " + strategy.getId() + " does not belong to user");
        }
        strategy.setAccountId(userAccountId);
        return strategyRepository.save(strategy);
    }

    @Override
    public void delete(Strategy strategy) {
        String accountId = userService.getUserAccountId();
        if (!strategy.getAccountId().equals(accountId)) {
            throw new InvalidUserDataException("Strategy with id " + strategy.getId() + " does not belong to user");
        }
        strategyRepository.deleteById(strategy.getId());
    }
}
