package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.SignInReq;
import com.marcomarchionni.strategistapi.dtos.request.SignUpReq;
import com.marcomarchionni.strategistapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtService jwtService;

    @Mock
    AuthenticationManager authenticationManager;

    AuthenticationServiceImpl authenticationServiceImpl;

    @BeforeEach
    void setUp() {
        authenticationServiceImpl = new AuthenticationServiceImpl(userRepository, passwordEncoder, jwtService,
                authenticationManager);
    }


    @Test
    void signUp() {
        // set up
        SignUpReq signUpReq = SignUpReq.builder().firstName("Marco").lastName("Marchionni").email("marco99@gmail.com")
                .password("password").build();

        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
        when(userRepository.save(any(User.class))).thenReturn(null);

        // execute
        var jwtAuthenticationResponse = authenticationServiceImpl.signUp(signUpReq);

        // verify
        assertEquals("jwtToken", jwtAuthenticationResponse.getToken());
    }

    @Test
    void signIn() {
        // set up
        SignInReq signInReq = SignInReq.builder().email("marco99@gmail.com").password("password").build();

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByEmail(any(String.class))).thenReturn(java.util.Optional.of(new User()));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        // execute
        var jwtAuthenticationResponse = authenticationServiceImpl.signIn(signInReq);

        // verify
        assertEquals("jwtToken", jwtAuthenticationResponse.getToken());
    }
}