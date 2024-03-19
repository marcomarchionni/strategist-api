package com.marcomarchionni.strategistapi.validators;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UpdateContextValidator implements ConstraintValidator<ValidUpdateContext, UpdateContext> {
    @Override
    public boolean isValid(UpdateContext dto, ConstraintValidatorContext context) {
        if (dto.getSourceType() == UpdateContext.SourceType.FILE) {
            return checkFileParameter(dto, context);
        } else if (dto.getSourceType() == UpdateContext.SourceType.SERVER) {
            return checkQueryIdAndTokenParameters(dto, context);
        }
        return true;
    }

    private boolean checkFileParameter(UpdateContext dto, ConstraintValidatorContext context) {
        if (dto.getFile() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File parameter is required")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean checkQueryIdAndTokenParameters(UpdateContext dto, ConstraintValidatorContext context) {
        boolean valid = true;
        if (dto.getQueryId() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("QueryId parameter is required")
                    .addConstraintViolation();
            valid = false;
        }
        if (dto.getToken() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Token parameter is required")
                    .addConstraintViolation();
            valid = false;
        }
        return valid;
    }
}
