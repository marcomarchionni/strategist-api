package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import com.marcomarchionni.ibportfolio.services.UserService;
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

    @Override
    public void deleteAll(@NotNull List<Position> positions) {
        if (positions.isEmpty()) {
            return;
        }
        validateList(positions);
        positionRepository.deleteAll(positions);
    }

    @Override
    public List<Position> saveAll(@NotNull List<Position> positions) {
        if (positions.isEmpty()) {
            return Collections.emptyList();
        }
        validateList(positions);
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
    public Optional<Position> findBySymbol(String symbol) {
        String accountId = userService.getUserAccountId();
        return positionRepository.findByAccountIdAndSymbol(accountId, symbol);
    }

    @Override
    public Optional<Position> findById(Long id) {
        String accountId = userService.getUserAccountId();
        return positionRepository.findByIdAndAccountId(id, accountId);
    }

    @Override
    public Position save(@NotNull Position position) {
        validatePosition(position);
        return positionRepository.save(position);
    }

    private void validateList(List<Position> positions) {
        String accountId = userService.getUserAccountId();
        positions.forEach(position -> validate(accountId, position));
    }

    private void validatePosition(Position position) {
        String accountId = userService.getUserAccountId();
        validate(accountId, position);
    }

    private void validate(String accountId, Position position) {
        // Throw InvalidUserDataException if the position does not belong to the user
        if (!accountId.equals(position.getAccountId())) {
            throw new InvalidUserDataException("Authenticated User and Position must have the same accountId");
        }
    }
}
