package com.marcomarchionni.strategistapi.services;


import com.marcomarchionni.strategistapi.domain.User;

public interface UserService {
    User getAuthenticatedUser();

    String getUserAccountId();
}
