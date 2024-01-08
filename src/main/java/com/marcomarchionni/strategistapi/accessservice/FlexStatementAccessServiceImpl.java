package com.marcomarchionni.strategistapi.accessservice;

import com.marcomarchionni.strategistapi.domain.FlexStatement;
import com.marcomarchionni.strategistapi.repositories.FlexStatementRepository;
import com.marcomarchionni.strategistapi.services.UserService;
import com.marcomarchionni.strategistapi.validators.AccountIdValidator;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlexStatementAccessServiceImpl implements FlexStatementAccessService {

    private final FlexStatementRepository flexStatementRepository;
    private final UserService userService;
    private final AccountIdValidator<FlexStatement> accountIdValidator;

    @Override
    public Optional<FlexStatement> findFirstOrderByToDateDesc() {
        String accountId = userService.getUserAccountId();
        return flexStatementRepository.findFirstByAccountIdOrderByToDateDesc(accountId);
    }

    @Override
    public FlexStatement save(@NotNull FlexStatement flexStatement) {
        String accountId = userService.getUserAccountId();
        accountIdValidator.hasValidAccountId(flexStatement, accountId);
        return flexStatementRepository.save(flexStatement);
    }
}
