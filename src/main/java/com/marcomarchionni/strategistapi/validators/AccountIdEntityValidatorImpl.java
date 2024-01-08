package com.marcomarchionni.strategistapi.validators;

import com.marcomarchionni.strategistapi.domain.AccountIdEntity;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.InvalidUserDataException;
import org.springframework.stereotype.Component;

@Component
public class AccountIdEntityValidatorImpl<T extends AccountIdEntity> implements AccountIdValidator<T> {
    @Override
    public boolean hasValidAccountId(T entity, String accountId) {
        if (entity.getAccountId() != null && entity.getAccountId().equals(accountId)) {
            return true;
        } else {
            throw new InvalidUserDataException();
        }
    }
}
