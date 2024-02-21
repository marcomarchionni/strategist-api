package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class AdminTokenGeneratorTest {

    @Autowired
    JwtService jwtService;

    User testAdmin = User.builder()
            .firstName("test-admin")
            .lastName("test-admin")
            .email("test.admin")
            .password("test.admin")
            .role(User.Role.ADMIN)
            .build();

    @Test
    void generateToken() {
        var token = jwtService.generateToken(testAdmin);
        System.out.println(token);
    }
}
