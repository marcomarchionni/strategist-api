package com.marcomarchionni.strategistapi.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class SourceTypeValidator implements ConstraintValidator<SourceType, String> {
    private final List<String> allowedSourceTypes = Arrays.asList("SERVER", "FILE", "SAMPLEDATA");

    @Override
    public void initialize(SourceType sourceTypeAnnotation) {
    }

    @Override
    public boolean isValid(String sourceType, ConstraintValidatorContext constraintValidatorContext) {
        return allowedSourceTypes.contains(sourceType.toUpperCase());
    }
}
