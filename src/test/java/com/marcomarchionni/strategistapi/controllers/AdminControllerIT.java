package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql("classpath:dbScripts/insertSampleData.sql")
class AdminControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .firstName("test-admin")
                .lastName("test-admin")
                .email("test.admin")
                .password("test.admin")
                .role(User.Role.ADMIN)
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void findAllUsers() throws Exception {
        List<User> users = userRepository.findAll();
        String userEmail = users.get(0).getEmail();

        mockMvc.perform(get("/admin/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(users.size())))
                .andExpect(jsonPath("$[0].email", is(userEmail)));
    }

    @Test
    void deleteUser() throws Exception {
        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
        String email = users.get(0).getEmail();

        mockMvc.perform(delete("/admin/users/{email}", email))
                .andDo(print())
                .andExpect(status().isOk());

        List<User> usersAfterDelete = userRepository.findAll();

        assertEquals(users.size() - 1, usersAfterDelete.size());
    }
}