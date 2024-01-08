package com.marcomarchionni.strategistapi.validators;

import com.marcomarchionni.strategistapi.domain.FlexStatement;
import com.marcomarchionni.strategistapi.dtos.update.UpdateDto;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.InvalidUserDataException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateDtoValidator implements AccountIdValidator<UpdateDto>, DtoValidator<UpdateDto> {

    private final Validator validator;

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    public boolean hasValidAccountId(UpdateDto dto, String accountId) {
        String dtoAccountId = Optional.ofNullable(dto.getFlexStatement()).map(FlexStatement::getAccountId).orElse(null);
        if (dtoAccountId != null && dtoAccountId.equals(accountId)) {
            return true;
        } else {
            throw new InvalidUserDataException();
        }
    }
}
