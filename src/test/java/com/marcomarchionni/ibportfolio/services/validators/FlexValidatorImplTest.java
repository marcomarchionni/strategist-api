package com.marcomarchionni.ibportfolio.services.validators;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FlexValidatorImplTest {

    FlexValidator flexValidator;

    Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
            flexValidator = new FlexValidatorImpl(validator);
        }
    }

    @Test
    void isValidNullDto() {

        var dto = new FlexQueryResponseDto();
        // Act
        assertThrows(ConstraintViolationException.class, () -> flexValidator.isValid(dto));
    }
}