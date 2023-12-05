package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.dtos.request.auth.SignInDto;
import com.marcomarchionni.ibportfolio.dtos.request.auth.SignUpDto;
import com.marcomarchionni.ibportfolio.dtos.response.auth.JwtAuthenticationResponse;

public interface AuthenticationService {
        JwtAuthenticationResponse signUp(SignUpDto request);

        JwtAuthenticationResponse signIn(SignInDto request);
}
