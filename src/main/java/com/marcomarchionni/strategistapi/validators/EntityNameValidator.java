package com.marcomarchionni.strategistapi.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EntityNameValidator implements ConstraintValidator<EntityName, String> {

    public void initialize(EntityName parameters) {
    }

    public boolean isValid(String portfolioName, ConstraintValidatorContext constraintValidatorContext) {
        return portfolioName.matches("^[A-Z]([a-zA-Z0-9_ ']{2,29})$");
    }
}
