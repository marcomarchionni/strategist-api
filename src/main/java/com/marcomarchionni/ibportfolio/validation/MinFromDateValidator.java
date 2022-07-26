package com.marcomarchionni.ibportfolio.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MinFromDateValidator implements ConstraintValidator<NotBefore1970, LocalDate> {


    @Override
    public void initialize(NotBefore1970 annotation) {
    }

    @Override
    public boolean isValid(LocalDate dateParam, ConstraintValidatorContext constraintValidatorContext) {
        return dateParam.isAfter(LocalDate.of(1970, 1, 1));
    }
}
