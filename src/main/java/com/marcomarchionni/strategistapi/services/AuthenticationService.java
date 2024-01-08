package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.auth.SignInDto;
import com.marcomarchionni.strategistapi.dtos.request.auth.SignUpDto;
import com.marcomarchionni.strategistapi.dtos.response.auth.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpDto request);

    JwtAuthenticationResponse signIn(SignInDto request);
}
