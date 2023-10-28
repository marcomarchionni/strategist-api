package com.marcomarchionni.ibportfolio.controllers.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class DateIntervalValidator implements ConstraintValidator<DateInterval, Object> {

    //TODO: get min, max date from application properties
    final LocalDate MIN_DATE = LocalDate.EPOCH;
    final LocalDate MAX_DATE = LocalDate.now().plusYears(5);
    String fromDateFieldName;
    String toDateFieldName;

    @Override
    public void initialize(DateInterval constraintAnnotation) {
        fromDateFieldName = constraintAnnotation.dateFrom();
        toDateFieldName = constraintAnnotation.dateTo();
    }

    @Override
    public boolean isValid(final Object dateInterval, ConstraintValidatorContext constraintValidatorContext) {

        try {
            final Field fromDateField = dateInterval.getClass().getDeclaredField(fromDateFieldName);
            fromDateField.setAccessible(true);
            final Field toDateField = dateInterval.getClass().getDeclaredField(toDateFieldName);
            toDateField.setAccessible(true);

            final LocalDate fromDate = (LocalDate) fromDateField.get(dateInterval);
            final LocalDate toDate = (LocalDate) toDateField.get(dateInterval);

            boolean fromDateInRange = (fromDate != null) && (fromDate.isAfter(MIN_DATE) && fromDate.isBefore(MAX_DATE));
            boolean toDateInRange = (toDate != null) && toDate.isAfter(MIN_DATE) && toDate.isBefore(MAX_DATE);

            boolean validBothNull = (fromDate == null) && (toDate == null);
            boolean validOneDateNullOtherDateInRange = ((fromDate == null) && toDateInRange) || (fromDateInRange && (toDate == null));
            boolean validInterval = fromDateInRange && toDateInRange && (fromDate.isEqual(toDate) || fromDate.isBefore(toDate));

            return validBothNull || validOneDateNullOtherDateInRange || validInterval;
        } catch (Exception e) {
            //TODO: log exception
            e.printStackTrace();
            return false;
        }
    }
}
