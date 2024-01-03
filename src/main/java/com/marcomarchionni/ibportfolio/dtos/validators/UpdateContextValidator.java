package com.marcomarchionni.ibportfolio.dtos.validators;

import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UpdateContextValidator implements ConstraintValidator<ValidUpdateContext, UpdateContextDto> {
    @Override
    public boolean isValid(UpdateContextDto dto, ConstraintValidatorContext context) {
        if (dto.getSourceType() == UpdateContextDto.SourceType.FILE) {
            return checkFileParameter(dto, context);
        } else if (dto.getSourceType() == UpdateContextDto.SourceType.SERVER) {
            return checkQueryIdAndTokenParameters(dto, context);
        }
        return true;
    }

    private boolean checkFileParameter(UpdateContextDto dto, ConstraintValidatorContext context) {
        if (dto.getFile() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File parameter is required")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean checkQueryIdAndTokenParameters(UpdateContextDto dto, ConstraintValidatorContext context) {
        if (dto.getQueryId() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("QueryId parameter is required")
                    .addConstraintViolation();
            return false;
        }
        if (dto.getToken() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Token parameter is required")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
