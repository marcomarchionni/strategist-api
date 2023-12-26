package com.marcomarchionni.ibportfolio.services.validators;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class FlexValidatorImpl implements FlexValidator {

    private final Validator validator;

    public FlexValidatorImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean isValid(FlexQueryResponseDto dto) {
        Set<ConstraintViolation<FlexQueryResponseDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return true;
    }
}
