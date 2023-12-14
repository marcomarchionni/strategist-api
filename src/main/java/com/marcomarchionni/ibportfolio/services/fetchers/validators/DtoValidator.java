package com.marcomarchionni.ibportfolio.services.fetchers.validators;

public interface DtoValidator {

    <T> boolean isValid(T dto);
}
