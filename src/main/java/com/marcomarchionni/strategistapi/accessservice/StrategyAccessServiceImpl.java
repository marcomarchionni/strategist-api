package com.marcomarchionni.strategistapi.accessservice;

import com.marcomarchionni.strategistapi.domain.Strategy;
import com.marcomarchionni.strategistapi.repositories.StrategyRepository;
import com.marcomarchionni.strategistapi.services.UserService;
import com.marcomarchionni.strategistapi.validators.AccountIdValidator;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StrategyAccessServiceImpl implements StrategyAccessService {

    private final UserService userService;
    private final StrategyRepository strategyRepository;
    private final AccountIdValidator<Strategy> accountIdValidator;

    @Override
    public List<Strategy> findByParams(String name) {
        String accountId = userService.getUserAccountId();
        return strategyRepository.findByParams(accountId, name);
    }

    @Override
    public Optional<Strategy> findById(@NotNull Long id) {
        String accountId = userService.getUserAccountId();
        return strategyRepository.findByIdAndAccountId(id, accountId);
    }

    @Override
    public Strategy save(@NotNull Strategy strategy) {
        String userAccountId = userService.getUserAccountId();
        accountIdValidator.hasValidAccountId(strategy, userAccountId);
        return strategyRepository.save(strategy);
    }

    @Override
    public void delete(@NotNull Strategy strategy) {
        String accountId = userService.getUserAccountId();
        accountIdValidator.hasValidAccountId(strategy, accountId);
        strategyRepository.deleteById(strategy.getId());
    }
}
