package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.services.AdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

  private final AdminService adminService;

  @Override
  public List<User> findAllUsers() {
    return adminService.findAllUsers();
  }

  @Override
  public void deleteUser(String email) {
    adminService.deleteUserAndUserData(email);
  }
}
