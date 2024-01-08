package com.marcomarchionni.strategistapi.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PortfolioNameValidator implements ConstraintValidator<PortfolioName, String> {

    public void initialize(PortfolioName parameters) {
    }

    public boolean isValid(String portfolioName, ConstraintValidatorContext constraintValidatorContext) {
        return portfolioName.matches("^[A-Z]([a-zA-Z0-9_ ']{2,29})$");
    }
}
