package com.marcomarchionni.ibportfolio.services.validators;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccountIdValidator.class)
public @interface ValidAccountId {
    String message() default "Account IDs must be consistent across all fields and be equal to the user's account ID.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
