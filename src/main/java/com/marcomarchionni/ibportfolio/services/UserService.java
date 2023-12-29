package com.marcomarchionni.ibportfolio.services;


import com.marcomarchionni.ibportfolio.domain.User;

public interface UserService {
    User getAuthenticatedUser();

    String getUserAccountId();
}
