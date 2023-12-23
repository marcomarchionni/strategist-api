package com.marcomarchionni.ibportfolio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.request.auth.SignInDto;
import com.marcomarchionni.ibportfolio.dtos.request.auth.SignUpDto;
import com.marcomarchionni.ibportfolio.dtos.response.auth.JwtAuthenticationResponse;
import com.marcomarchionni.ibportfolio.services.AuthenticationService;
import com.marcomarchionni.ibportfolio.services.DividendService;
import com.marcomarchionni.ibportfolio.services.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @MockBean
    DividendService dividendService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    AuthenticationService authenticationService;

    ObjectMapper mapper;

    User user;

    @BeforeEach
    void setUp() {
        user = getSampleUser();
        mapper = new ObjectMapper();
        var authToken = JwtAuthenticationResponse.builder().token("token").build();
        when(authenticationService.signUp(any())).thenReturn(authToken);
        when(authenticationService.signIn(any())).thenReturn(authToken);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void signupValidParameters() throws Exception {
        // setup data
        SignUpDto signUpDto = SignUpDto.builder()
                .firstName("Marco")
                .lastName("Marchionni")
                .email("marco99@gmail.com")
                .password("encodedPassword")
                .accountId("U1234567")
                .build();

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @ParameterizedTest
    @CsvSource({
            "Marco, Marchionni,email,password,U1234567",
            "M, Marchionni,marco@gmail.com,password,U1234567",
            "Marco, Marchionni,marco@gmail.com,password,U111"
    })
    void signupInvalidParameters(String firstName, String lastName, String email, String password, String accountId) throws Exception {
        SignUpDto signUpDto = SignUpDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .accountId(accountId)
                .build();

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(content().json("{\"type\":\"invalid-query-parameter\"}"));
    }

    @Test
    void signinValidData() throws Exception {
        var signInDto = SignInDto.builder()
                .email("marco@gmail.com")
                .password("password")
                .build();

        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signInDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @ParameterizedTest
    @CsvSource({
            "email,password",
            "marco@gmail.com, pass",
    })
    void signinInvalidData(String email, String password) throws Exception {
        var signInDto = SignInDto.builder()
                .email(email)
                .password(password)
                .build();

        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signInDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(content().json("{\"type\":\"invalid-query-parameter\"}"));
    }
}

