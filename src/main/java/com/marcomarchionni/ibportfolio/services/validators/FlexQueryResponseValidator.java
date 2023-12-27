package com.marcomarchionni.ibportfolio.services.validators;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlexQueryResponseValidator implements DtoValidator<FlexQueryResponseDto> {

    private final Validator validator;

    @Override
    public Validator getValidator() {
        return validator;
    }
}
