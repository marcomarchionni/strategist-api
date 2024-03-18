package com.marcomarchionni.strategistapi.validators;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FlexValidatorImplTest {

    DtoValidator<FlexQueryResponseDto> flexValidator;

    Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
            flexValidator = new DtoValidatorImpl<>(validator) {

            };
        }
    }

    @Test
    void isValidNullDto() {

        var dto = FlexQueryResponseDto.builder().build();
        // Act
        assertThrows(ConstraintViolationException.class, () -> flexValidator.validate(dto));
    }
}