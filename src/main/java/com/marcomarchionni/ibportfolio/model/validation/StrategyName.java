package com.marcomarchionni.ibportfolio.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( {ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = StrategyNameValidator.class)
public @interface StrategyName {
    String message() default "StrategyName must start with capital letter, contain 3-30 characters. Letters, numbers, spaces, underscore and apostrophe allowed";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}

