package com.marcomarchionni.strategistapi.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrategyNameValidator implements ConstraintValidator<StrategyName, String> {

    public void initialize(StrategyName parameters) {
    }

    public boolean isValid(String strategyName, ConstraintValidatorContext constraintValidatorContext) {
        return strategyName.matches("^[A-Z]([a-zA-Z0-9_ ']{2,29})$");
    }
}
