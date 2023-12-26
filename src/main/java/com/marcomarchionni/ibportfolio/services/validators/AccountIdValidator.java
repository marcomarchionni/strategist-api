package com.marcomarchionni.ibportfolio.services.validators;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountIdValidator implements ConstraintValidator<ValidAccountId, FlexQueryResponseDto> {

    @Override
    public boolean isValid(FlexQueryResponseDto dto, ConstraintValidatorContext constraintValidatorContext) {
        String accountId = dto.nullSafeGetAccountId();
        if (accountId == null) {
            return false;
        }
        boolean validOpenPositions = areValidOpenPositions(accountId, dto);
        boolean validTrades = areValidTrades(accountId, dto);
        boolean validOrders = areValidOrders(accountId, dto);
        boolean validChangeInDividendAccruals = areValidChangeInDividendAccruals(accountId, dto);
        boolean validOpenDividendsAccruals = areValidOpenDividendsAccruals(accountId, dto);
        return validOpenPositions && validTrades && validOrders && validChangeInDividendAccruals && validOpenDividendsAccruals;
    }

    private boolean areValidOpenPositions(String accountId, FlexQueryResponseDto dto) {
        return dto.nullSafeGetOpenPositions().stream()
                .allMatch(openPosition -> openPosition.getAccountId().equals(accountId));
    }

    private boolean areValidTrades(String accountId, FlexQueryResponseDto dto) {
        return dto.nullSafeGetTrades().stream().allMatch(trade -> trade.getAccountId().equals(accountId));
    }

    private boolean areValidOrders(String accountId, FlexQueryResponseDto dto) {
        return dto.nullSafeGetOrders().stream().allMatch(order -> order.getAccountId().equals(accountId));
    }

    private boolean areValidChangeInDividendAccruals(String accountId, FlexQueryResponseDto dto) {
        return dto.nullSafeGetChangeInDividendAccruals().stream()
                .allMatch(changeInDividendAccrual -> changeInDividendAccrual.getAccountId().equals(accountId));
    }

    private boolean areValidOpenDividendsAccruals(String accountId, FlexQueryResponseDto dto) {
        return dto.nullSafeGetOpenDividendAccruals().stream()
                .allMatch(openDividendAccrual -> openDividendAccrual.getAccountId().equals(accountId));
    }
}
