package com.marcomarchionni.strategistapi.validators;

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
@Constraint(validatedBy = EntityNameValidator.class)
public @interface EntityName {
    String message() default "Name must start with capital letter, contain 3-30 characters. Letters, numbers, spaces," +
            " underscore and apostrophe allowed";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}

