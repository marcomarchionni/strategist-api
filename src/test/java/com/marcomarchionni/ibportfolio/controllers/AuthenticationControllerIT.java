package com.marcomarchionni.ibportfolio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.auth.SignInDto;
import com.marcomarchionni.ibportfolio.dtos.request.auth.SignUpDto;
import com.marcomarchionni.ibportfolio.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void signup() throws Exception {
        SignUpDto signUpDto = SignUpDto.builder()
                .firstName("Marco")
                .lastName("Marchionni")
                .email("marco99@gmail.com").password("password").build();

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token", is(notNullValue())));
    }

    @Test
    void signin() throws Exception {
        // setup user
        User user = User.builder()
                .firstName("Marco")
                .lastName("Marchionni")
                .email("marco99@gmail.com").password(passwordEncoder.encode("password")).role(User.Role.USER).build();

        userRepository.save(user);

        SignInDto signInDto = SignInDto.builder()
                .email("marco99@gmail.com").password("password").build();

        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signInDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token", is(notNullValue())));
    }

    @Test
    void unauthorized() throws Exception {

        SignInDto signInDto = SignInDto.builder()
                .email("marco99@gmail.com").password("password").build();

        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signInDto)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type", is("unauthorized")));
    }
}