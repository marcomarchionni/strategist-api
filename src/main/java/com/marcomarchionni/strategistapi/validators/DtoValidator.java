package com.marcomarchionni.strategistapi.validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Set;

public interface DtoValidator<T> {
    default void validate(T dto) {
        Set<ConstraintViolation<T>> violations = getValidator().validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    Validator getValidator();
}