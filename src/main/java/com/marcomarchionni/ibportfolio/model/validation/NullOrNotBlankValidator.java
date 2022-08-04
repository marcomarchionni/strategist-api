package com.marcomarchionni.ibportfolio.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {

    public void initialize(NullOrNotBlank parameters) {
    }

    public boolean isValid(String stringValue, ConstraintValidatorContext constraintValidatorContext) {
        return stringValue == null || stringValue.trim().length() > 0;
    }
}
