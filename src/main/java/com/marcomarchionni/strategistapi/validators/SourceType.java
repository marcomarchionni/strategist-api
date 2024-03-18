package com.marcomarchionni.strategistapi.validators;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SourceTypeValidator.class)
public @interface SourceType {
    String message() default "SourceTye must be one of: SERVER, FILE, SAMPLEDATA, case insensitive.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
