package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.services.UserService;
import com.marcomarchionni.ibportfolio.validators.AccountIdValidator;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionAccessServiceImpl implements PositionAccessService {
    private final PositionRepository positionRepository;
    private final UserService userService;
    private final AccountIdValidator<Position> accountIdValidator;

    @Override
    public void deleteAll(@NotNull List<Position> positions) {
        if (positions.isEmpty()) {
            return;
        }
        validateAccountIds(positions);
        positionRepository.deleteAll(positions);
    }

    @Override
    public List<Position> saveAll(@NotNull List<Position> positions) {
        if (positions.isEmpty()) {
            return Collections.emptyList();
        }
        validateAccountIds(positions);
        return positionRepository.saveAll(positions);
    }

    @Override
    public List<Position> findByParams(Boolean tagged, String symbol, String assetCategory) {
        String accountId = userService.getUserAccountId();
        return positionRepository.findByParams(accountId, tagged, symbol, assetCategory);
    }

    @Override
    public List<Position> findAll() {
        String accountId = userService.getUserAccountId();
        return positionRepository.findAllByAccountId(accountId);
    }

    @Override
    public Optional<Position> findBySymbol(@NotNull String symbol) {
        String accountId = userService.getUserAccountId();
        return positionRepository.findByAccountIdAndSymbol(accountId, symbol);
    }

    @Override
    public Optional<Position> findById(@NotNull Long id) {
        String accountId = userService.getUserAccountId();
        return positionRepository.findByIdAndAccountId(id, accountId);
    }

    @Override
    public Position save(@NotNull Position position) {
        String accountId = userService.getUserAccountId();
        accountIdValidator.hasValidAccountId(position, accountId);
        return positionRepository.save(position);
    }

    private void validateAccountIds(List<Position> positions) {
        String accountId = userService.getUserAccountId();
        positions.forEach(position -> accountIdValidator.hasValidAccountId(position, accountId));
    }
}
