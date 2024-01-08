package com.marcomarchionni.strategistapi.validators;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoValidatorImpl<T> implements DtoValidator<T> {

    private final Validator validator;

    @Override
    public Validator getValidator() {
        return validator;
    }
}
