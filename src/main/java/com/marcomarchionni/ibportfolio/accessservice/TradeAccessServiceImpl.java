package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import com.marcomarchionni.ibportfolio.services.UserService;
import com.marcomarchionni.ibportfolio.validators.AccountIdValidator;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradeAccessServiceImpl implements TradeAccessService {

    private final UserService userService;
    private final TradeRepository tradeRepository;
    private final AccountIdValidator<Trade> accountIdValidator;

    @Override
    public List<Trade> findByParams(LocalDate startDate, LocalDate endDate, Boolean tagged, String symbol,
                                    String assetCategory) {
        String accountId = userService.getUserAccountId();
        return tradeRepository.findByParams(accountId, startDate, endDate, tagged, symbol, assetCategory);
    }

    @Override
    public boolean existsByIbOrderId(Long ibOrderId) {
        String accountId = userService.getUserAccountId();
        return tradeRepository.existsByAccountIdAndIbOrderId(accountId, ibOrderId);
    }

    @Override
    public Trade save(@NotNull Trade trade) {
        String accountId = userService.getUserAccountId();
        accountIdValidator.hasValidAccountId(trade, accountId);
        return tradeRepository.save(trade);
    }

    @Override
    public List<Trade> saveAll(@NotNull List<Trade> trades) {
        String accountId = userService.getUserAccountId();
        trades.forEach(trade -> accountIdValidator.hasValidAccountId(trade, accountId));
        return tradeRepository.saveAll(trades);
    }

    @Override
    public Optional<Trade> findById(Long id) {
        String accountId = userService.getUserAccountId();
        return tradeRepository.findByIdAndAccountId(id, accountId);
    }
}
