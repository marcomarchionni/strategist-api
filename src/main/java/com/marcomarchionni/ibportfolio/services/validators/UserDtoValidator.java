package com.marcomarchionni.ibportfolio.services.validators;

public interface UserDtoValidator<T> extends DtoValidator<T> {
    boolean hasValidAccountId(T dto, String accountId);
}
