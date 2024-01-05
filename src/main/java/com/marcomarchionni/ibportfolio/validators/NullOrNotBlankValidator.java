package com.marcomarchionni.ibportfolio.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {

    public void initialize(NullOrNotBlank parameters) {
    }

    public boolean isValid(String stringValue, ConstraintValidatorContext constraintValidatorContext) {
        return stringValue == null || !stringValue.trim().isEmpty();
    }
}
