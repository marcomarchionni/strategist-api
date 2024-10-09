package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.SignInReq;
import com.marcomarchionni.strategistapi.dtos.request.SignUpReq;
import com.marcomarchionni.strategistapi.dtos.response.auth.JwtAuthenticationResponse;
import com.marcomarchionni.strategistapi.dtos.response.auth.SigninResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpReq request);

    SigninResponse signIn(SignInReq request);
}
