package com.marcomarchionni.strategistapi.util;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.services.JwtService;
import com.marcomarchionni.strategistapi.services.JwtServiceImpl;

import java.io.FileInputStream;
import java.util.Properties;

public class AdminTokenGenerator {

    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/application-aws" +
                ".properties")) {
            // Access application.properties without invoking the Spring context
            Properties properties = new Properties();
            properties.load(fileInputStream);
            String jwtSecret = properties.getProperty("token.signing.key");

            // Setup JwtService
            JwtService jwtService = new JwtServiceImpl(jwtSecret, 1000 * 60 * 60 * 24 * 7);

            // Setup AdminUser
            User user = User.builder()
                    .firstName("test-admin")
                    .lastName("test-admin")
                    .email("test.admin")
                    .password("test.admin")
                    .role(User.Role.ADMIN)
                    .build();

            // Generate token
            String token = jwtService.generateToken(user);
            System.out.println(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


