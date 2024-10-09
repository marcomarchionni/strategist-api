package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.response.auth.UserSummary;

public interface UserMapper {
    UserSummary toUserSummary(User user);
}
