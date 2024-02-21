package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final UserService userService;

    @Override
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @Override
    public void deleteUser(String email) {
        userService.deleteUser(email);
    }
}
