package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.ibportfolio.repositories.FlexStatementRepository;
import com.marcomarchionni.ibportfolio.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlexStatementAccessServiceImpl implements FlexStatementAccessService {

    private final FlexStatementRepository flexStatementRepository;
    private final UserService userService;

    @Override
    public Optional<FlexStatement> findFirstOrderByToDateDesc() {
        String accountId = userService.getUserAccountId();
        return flexStatementRepository.findFirstByAccountIdOrderByToDateDesc(accountId);
    }

    @Override
    public FlexStatement save(FlexStatement flexStatement) {
        String accountId = userService.getUserAccountId();
        if (!accountId.equals(flexStatement.getAccountId())) {
            throw new InvalidUserDataException("Authenticated User and FlexStatement must have the same accountId");
        }
        return flexStatementRepository.save(flexStatement);
    }
}
