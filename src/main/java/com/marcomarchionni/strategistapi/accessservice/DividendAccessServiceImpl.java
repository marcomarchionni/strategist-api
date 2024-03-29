package com.marcomarchionni.strategistapi.accessservice;

import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.repositories.DividendRepository;
import com.marcomarchionni.strategistapi.services.UserService;
import com.marcomarchionni.strategistapi.validators.AccountIdValidator;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DividendAccessServiceImpl implements DividendAccessService {
    private final DividendRepository dividendRepository;
    private final UserService userService;
    private final AccountIdValidator<Dividend> accountIdValidator;

    @Override
    public List<Dividend> findByParams(LocalDate exDateFrom, LocalDate exDateTo, LocalDate payDateFrom,
                                       LocalDate payDateTo, Boolean tagged, String symbol) {
        String accountId = userService.getUserAccountId();
        return dividendRepository.findByParams(accountId, exDateFrom, exDateTo, payDateFrom, payDateTo, tagged, symbol);
    }

    @Override
    public Optional<Dividend> findById(@NotNull Long id) {
        String accountId = userService.getUserAccountId();
        return dividendRepository.findByIdAndAccountId(id, accountId);
    }

    @Override
    public List<Dividend> findBySymbol(@NotNull String symbol) {
        String accountId = userService.getUserAccountId();
        return dividendRepository.findByAccountIdAndSymbol(accountId, symbol);
    }

    @Override
    public boolean existsByActionId(@NotNull Long actionId) {
        String accountId = userService.getUserAccountId();
        return dividendRepository.existsByAccountIdAndActionId(accountId, actionId);
    }

    @Override
    public List<Dividend> findOpenDividends() {
        String accountId = userService.getUserAccountId();
        return dividendRepository.findByOpenClosedAndAccountId(Dividend.OpenClosed.OPEN, accountId);
    }

    @Override
    public Dividend save(Dividend dividend) {
        String accountId = userService.getUserAccountId();
        accountIdValidator.hasValidAccountId(dividend, accountId);
        return dividendRepository.save(dividend);
    }

    @Override
    public List<Dividend> saveAll(@NotNull List<Dividend> dividends) {
        String accountId = userService.getUserAccountId();
        dividends.forEach(dividend -> accountIdValidator.hasValidAccountId(dividend, accountId));
        return dividendRepository.saveAll(dividends);
    }
}
