package com.marcomarchionni.ibportfolio.models.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;

public class DateIntervalValidator implements ConstraintValidator<DateInterval, Object> {

    String fromDateFieldName;
    String toDateFieldName;

    final LocalDate MIN_DATE = LocalDate.EPOCH;
    final LocalDate MAX_DATE = LocalDate.now().plusYears(5);

    @Override
    public void initialize(DateInterval constraintAnnotation) {
        fromDateFieldName = constraintAnnotation.dateFrom();
        toDateFieldName = constraintAnnotation.dateTo();
    }

    @Override
    public boolean isValid(final Object object, ConstraintValidatorContext constraintValidatorContext) {

        try {
            final Field fromDateField = object.getClass().getDeclaredField(fromDateFieldName);
            fromDateField.setAccessible(true);
            final Field toDateField = object.getClass().getDeclaredField(toDateFieldName);
            toDateField.setAccessible(true);

            final LocalDate fromDate = (LocalDate) fromDateField.get(object);
            final LocalDate toDate = (LocalDate) toDateField.get(object);

            boolean fromDateInRange = (fromDate != null) && (fromDate.isAfter(MIN_DATE) && fromDate.isBefore(MAX_DATE));
            boolean toDateInRange = (toDate != null) && toDate.isAfter(MIN_DATE) && toDate.isBefore(MAX_DATE);

            boolean validBothNull = (fromDate == null) && (toDate == null);
            boolean validOneDateNullOtherDateInRange = ((fromDate == null) && toDateInRange) || (fromDateInRange && (toDate == null));
            boolean validInterval = fromDateInRange && toDateInRange && (fromDate.isEqual(toDate) || fromDate.isBefore(toDate));

            return validBothNull || validOneDateNullOtherDateInRange || validInterval;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
