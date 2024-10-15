package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceImplTest {

    JwtService jwtService;

    User user;

    String token;

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl("secretsecretsecretsecretsecretsecretsecretsecret", 1000 * 60 * 24);
        user = User.builder()
                .id(1L)
                .firstName("Marco")
                .lastName("Marchionni")
                .email("marco99@gmail.com").password("password").role(User.Role.USER).build();

        // execute
        token = jwtService.generateAccessToken(user);
    }

    @Test
    void extractUserName() {
        // setup
        String expectedUserName = user.getEmail();

        // execute
        String userName = jwtService.extractUserName(token);

        // verify
        assertEquals(expectedUserName, userName);
    }

    @Test
    void generateAccessToken() {
        // setup
        User user = User.builder()
                .id(1L)
                .firstName("Marco")
                .lastName("Marchionni")
                .email("marco99@gmail.com").password("password").role(User.Role.USER).build();

        // execute
        String token = jwtService.generateAccessToken(user);
        String userName = jwtService.extractUserName(token);

        // verify
        assertEquals(user.getEmail(), userName);
    }

    @Test
    void isTokenValid() {
        boolean isValid = jwtService.isTokenValid(token, user);
        assertTrue(isValid);
    }

    @Test
    void tokenInvalid() {
        JwtService invalidService = new JwtServiceImpl("secretsecretsecretsecretsecretsecretsecretsecret", 0);
        String expiredToken = invalidService.generateAccessToken(user);
        assertThrows(ExpiredJwtException.class, () -> invalidService.isTokenValid(expiredToken, user));
    }

    @Test
    void generateAdminToken() {
        // setup
        new JwtServiceImpl("secretsecretsecretsecretsecretsecretsecretsecret", 1000 * 60 * 24);
        User testAdmin = User.builder()
                .id(1L)
                .firstName("test.admin")
                .lastName("test.admin")
                .email("test.admin").password("test.admin").role(User.Role.ADMIN).build();

        // execute
        String token = jwtService.generateAccessToken(testAdmin);
        String userName = jwtService.extractUserName(token);

        // verify
        assertEquals(testAdmin.getEmail(), userName);
    }

}