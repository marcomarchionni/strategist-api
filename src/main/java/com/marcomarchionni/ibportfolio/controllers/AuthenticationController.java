package com.marcomarchionni.ibportfolio.controllers;

import com.marcomarchionni.ibportfolio.dtos.request.auth.SignInDto;
import com.marcomarchionni.ibportfolio.dtos.request.auth.SignUpDto;
import com.marcomarchionni.ibportfolio.dtos.response.auth.JwtAuthenticationResponse;
import com.marcomarchionni.ibportfolio.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpDto request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInDto request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}
