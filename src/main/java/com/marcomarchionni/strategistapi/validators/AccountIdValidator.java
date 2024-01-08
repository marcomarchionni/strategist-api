package com.marcomarchionni.strategistapi.validators;

public interface AccountIdValidator<T> {
    boolean hasValidAccountId(T dto, String accountId);
}
