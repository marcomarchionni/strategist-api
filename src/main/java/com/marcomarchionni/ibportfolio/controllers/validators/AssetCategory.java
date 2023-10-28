package com.marcomarchionni.ibportfolio.controllers.validators;

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
@Constraint(validatedBy = AssetCategoryValidator.class)
public @interface AssetCategory {
    String message() default "Not a valid asset category";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}
