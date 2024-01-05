package com.marcomarchionni.ibportfolio.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//TODO: message does not appear on Response Object. Bind exception is raised...
@Repeatable(DateIntervals.class)
@Constraint(validatedBy = DateIntervalValidator.class)
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface DateInterval {

    String message() default "dateFrom, dateTo should be within Min and Max date. dateFrom should be equal or before dateTo";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String dateFrom();

    String dateTo();
}
