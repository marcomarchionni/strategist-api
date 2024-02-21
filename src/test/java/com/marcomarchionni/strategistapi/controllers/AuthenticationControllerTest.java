package com.marcomarchionni.strategistapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.SignInReq;
import com.marcomarchionni.strategistapi.dtos.request.SignUpReq;
import com.marcomarchionni.strategistapi.dtos.response.auth.JwtAuthenticationResponse;
import com.marcomarchionni.strategistapi.services.AuthenticationService;
import com.marcomarchionni.strategistapi.services.DividendService;
import com.marcomarchionni.strategistapi.services.JwtService;
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

import static com.marcomarchionni.strategistapi.util.TestUtils.getSampleUser;
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
        SignUpReq signUpReq = SignUpReq.builder()
                .firstName("Marco")
                .lastName("Marchionni")
                .email("marco99@gmail.com")
                .password("encodedPassword")
                .accountId("U1234567")
                .build();

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpReq)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @ParameterizedTest
    @CsvSource({
            "Marco,Marchionni,email,password,U1234567,USER",
            "M, Marchionni,marco@gmail.com,password,U1234567,USER",
            "Marco,Marchionni,marco@gmail.com,password,U111,USER",
            "Marco,Marchionni,marco@gmail.com,password,U1234567,INVALID"
    })
    void signupInvalidParameters(String firstName, String lastName, String email, String password, String accountId,
                                 String role) throws Exception {
        SignUpReq signUpReq = SignUpReq.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .accountId(accountId)
                .role(role)
                .build();

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpReq)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(content().json("{\"type\":\"invalid-query-parameter\"}"));
    }

    @Test
    void signinValidData() throws Exception {
        var signInDto = SignInReq.builder()
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
        var signInDto = SignInReq.builder()
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

