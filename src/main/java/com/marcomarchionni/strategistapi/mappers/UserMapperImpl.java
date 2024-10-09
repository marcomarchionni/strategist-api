package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.response.auth.UserSummary;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserSummary toUserSummary(User user) {
        return UserSummary.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .accountId(user.getAccountId())
                .build();
    }
}
