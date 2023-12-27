package com.marcomarchionni.ibportfolio.services.validators;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidUserDataException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateDtoValidator implements UserDtoValidator<UpdateDto> {

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
