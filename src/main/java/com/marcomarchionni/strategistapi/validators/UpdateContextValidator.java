package com.marcomarchionni.strategistapi.validators;

import com.marcomarchionni.strategistapi.dtos.request.UpdateContextReq;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UpdateContextValidator implements ConstraintValidator<ValidUpdateContext, UpdateContextReq> {
    @Override
    public boolean isValid(UpdateContextReq dto, ConstraintValidatorContext context) {
        if (dto.getSourceType() == UpdateContextReq.SourceType.FILE) {
            return checkFileParameter(dto, context);
        } else if (dto.getSourceType() == UpdateContextReq.SourceType.SERVER) {
            return checkQueryIdAndTokenParameters(dto, context);
        }
        return true;
    }

    private boolean checkFileParameter(UpdateContextReq dto, ConstraintValidatorContext context) {
        if (dto.getFile() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File parameter is required")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean checkQueryIdAndTokenParameters(UpdateContextReq dto, ConstraintValidatorContext context) {
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
