package com.marcomarchionni.ibportfolio.models.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = DateIntervalValidator.class)
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface DateInterval {

        String message() default "{com.marcomarchionni.ibportfolio.models.validation.DateInterval.message}";
        Class <?> [] groups() default {};
        Class <? extends Payload> [] payload() default {};

        String dateFrom();
        String dateTo();
}
