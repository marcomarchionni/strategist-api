package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.domain.User;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/admin")
@Hidden
public interface AdminApi {

    @GetMapping("/users")
    @Operation(summary = "Find all users")
    List<User> findAllUsers();

    @DeleteMapping("/users/{email}")
    @Operation(summary = "Delete user by email")
    void deleteUser(@PathVariable("email") String email);
}
