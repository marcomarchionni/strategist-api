package com.marcomarchionni.ibportfolio.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinFromDateValidator.class)
@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBefore1970 {
    String message() default "Date should be after 1970-01-01";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

