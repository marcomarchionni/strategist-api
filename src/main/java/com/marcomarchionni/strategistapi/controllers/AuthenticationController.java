package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.RefreshTokenRequest;
import com.marcomarchionni.strategistapi.dtos.request.SignInReq;
import com.marcomarchionni.strategistapi.dtos.request.SignUpReq;
import com.marcomarchionni.strategistapi.dtos.response.auth.RefreshTokenResponse;
import com.marcomarchionni.strategistapi.dtos.response.auth.SigninResponse;
import com.marcomarchionni.strategistapi.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<Void> signup(@Valid @RequestBody SignUpReq request) {
        authenticationService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<SigninResponse> signin(@Valid @RequestBody SignInReq request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
