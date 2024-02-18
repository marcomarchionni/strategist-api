package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.SignInReq;
import com.marcomarchionni.strategistapi.dtos.request.SignUpReq;
import com.marcomarchionni.strategistapi.dtos.response.auth.JwtAuthenticationResponse;
import com.marcomarchionni.strategistapi.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    public ResponseEntity<JwtAuthenticationResponse> signup(@Valid @RequestBody SignUpReq request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    public ResponseEntity<JwtAuthenticationResponse> signin(@Valid @RequestBody SignInReq request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}
