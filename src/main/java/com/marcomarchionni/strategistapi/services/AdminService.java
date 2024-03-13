package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.User;

import java.util.List;

public interface AdminService {

    void deleteUserAndUserData(String email);

    List<User> findAllUsers();
}
