package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.User;
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
        token = jwtService.generateToken(user);
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
    void generateToken() {
        // setup
        User user = User.builder()
                .id(1L)
                .firstName("Marco")
                .lastName("Marchionni")
                .email("marco99@gmail.com").password("password").role(User.Role.USER).build();

        // execute
        String token = jwtService.generateToken(user);
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
        String expiredToken = invalidService.generateToken(user);
        assertThrows(ExpiredJwtException.class, () -> invalidService.isTokenValid(expiredToken, user));
    }
}