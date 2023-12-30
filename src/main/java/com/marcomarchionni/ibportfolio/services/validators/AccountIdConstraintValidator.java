package com.marcomarchionni.ibportfolio.services.validators;

import com.marcomarchionni.ibportfolio.dtos.update.UpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountIdConstraintValidator implements ConstraintValidator<ValidAccountId, UpdateDto> {

    @Override
    public boolean isValid(UpdateDto dto, ConstraintValidatorContext constraintValidatorContext) {
        try {
            String accountId = dto.getFlexStatement().getAccountId();
            boolean validOpenPositions = dto.getPositions().stream()
                    .allMatch(position -> position.getAccountId().equals(accountId));
            boolean validTrades = dto.getTrades().stream()
                    .allMatch(trade -> trade.getAccountId().equals(accountId));
            boolean validDividends = dto.getDividends().stream()
                    .allMatch(dividend -> dividend.getAccountId().equals(accountId));
            return validOpenPositions && validTrades && validDividends;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
