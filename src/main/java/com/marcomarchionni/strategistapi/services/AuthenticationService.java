package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.RefreshTokenRequest;
import com.marcomarchionni.strategistapi.dtos.request.SignInReq;
import com.marcomarchionni.strategistapi.dtos.request.SignUpReq;
import com.marcomarchionni.strategistapi.dtos.response.auth.RefreshTokenResponse;
import com.marcomarchionni.strategistapi.dtos.response.auth.SignUpResponse;
import com.marcomarchionni.strategistapi.dtos.response.auth.SigninResponse;

public interface AuthenticationService {
    SignUpResponse signUp(SignUpReq request);

    SigninResponse signIn(SignInReq request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
