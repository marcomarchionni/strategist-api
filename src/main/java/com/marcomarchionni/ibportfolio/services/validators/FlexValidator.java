package com.marcomarchionni.ibportfolio.services.validators;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;

public interface FlexValidator {
    boolean isValid(FlexQueryResponseDto dto);
}
