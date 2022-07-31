package com.marcomarchionni.ibportfolio.models.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

    public void initialize(Name parameters) {
    }

    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("^[A-Z]([a-zA-Z0-9_ ']{2,29})$");
    }
}
