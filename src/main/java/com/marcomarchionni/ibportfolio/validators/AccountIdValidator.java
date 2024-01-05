package com.marcomarchionni.ibportfolio.validators;

public interface AccountIdValidator<T> {
    boolean hasValidAccountId(T dto, String accountId);
}
