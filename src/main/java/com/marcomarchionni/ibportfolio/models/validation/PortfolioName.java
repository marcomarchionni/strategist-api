package com.marcomarchionni.ibportfolio.models.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( {ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = PortfolioNameValidator.class)
public @interface PortfolioName {
    String message() default "PortfolioName must start with capital letter, contain 3-30 characters. Letters, numbers, spaces, underscore and apostrophe allowed";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}

