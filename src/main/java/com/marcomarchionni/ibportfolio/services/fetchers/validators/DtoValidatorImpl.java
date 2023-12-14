package com.marcomarchionni.ibportfolio.services.fetchers.validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DtoValidatorImpl implements DtoValidator {
    private final Validator validator;

    public DtoValidatorImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <T> boolean isValid(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        // Log or handle violations
        return violations.isEmpty();
    }
}
