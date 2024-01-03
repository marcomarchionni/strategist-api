package com.marcomarchionni.ibportfolio.dtos.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UpdateContextValidator.class)
public @interface ValidUpdateContext {
    String message() default "Invalid update context";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
