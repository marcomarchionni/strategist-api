package com.marcomarchionni.strategistapi.services;


import com.marcomarchionni.strategistapi.domain.User;

import java.util.List;

public interface UserService {
    User getAuthenticatedUser();

    String getUserAccountId();

    List<User> findAllUsers();

    void deleteUser(String email);
}
