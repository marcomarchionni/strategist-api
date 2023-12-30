package com.marcomarchionni.ibportfolio.services.validators;

public interface AccountIdValidator<T> {
    boolean hasValidAccountId(T dto, String accountId);
}
