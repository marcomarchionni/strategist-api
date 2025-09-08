package com.marcomarchionni.strategistapi.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Objects;

public class DateIntervalValidator implements ConstraintValidator<DateInterval, Object> {
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
  public boolean isValid(
      final Object dateInterval, ConstraintValidatorContext constraintValidatorContext) {

    try {
      final Field fromDateField = dateInterval.getClass().getDeclaredField(fromDateFieldName);
      fromDateField.setAccessible(true);
      final Field toDateField = dateInterval.getClass().getDeclaredField(toDateFieldName);
      toDateField.setAccessible(true);

      final LocalDate fromDate = (LocalDate) fromDateField.get(dateInterval);
      final LocalDate toDate = (LocalDate) toDateField.get(dateInterval);

      boolean fromDateInRange =
          (fromDate != null) && (fromDate.isAfter(MIN_DATE) && fromDate.isBefore(MAX_DATE));
      boolean toDateInRange =
          (toDate != null) && toDate.isAfter(MIN_DATE) && toDate.isBefore(MAX_DATE);

      boolean validBothNull = (fromDate == null) && (toDate == null);
      boolean validOneDateNullOtherDateInRange =
          ((fromDate == null) && toDateInRange) || (fromDateInRange && (toDate == null));
      boolean validInterval = false;
      if (fromDateInRange && toDateInRange) {
        LocalDate from = Objects.requireNonNull(fromDate);
        LocalDate to = Objects.requireNonNull(toDate);
        validInterval = !from.isAfter(to);
      }

      return validBothNull || validOneDateNullOtherDateInRange || validInterval;
    } catch (Exception e) {
      return false;
    }
  }
}
