package com.marcomarchionni.strategistapi.controllers;

import com.marcomarchionni.strategistapi.dtos.request.SignInReq;
import com.marcomarchionni.strategistapi.dtos.request.SignUpReq;
import com.marcomarchionni.strategistapi.dtos.response.auth.JwtAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "1. Authentication", description = "User signin or signup")
@RequestMapping("/auth")
public interface AuthenticationApi {
    @PostMapping("/signup")
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType =
                    "application/json", examples = @ExampleObject(value = "{\"token\": \"eyJhbGciOiJIUzI1NiJ9" +
                    ".eyJzdWIiOiJtYXJjbzk5QGdtYWlsLmNvbSIsImlhdCI6MTcwMjQ4OTQxNSwiZXhwIjoxNzAyNTc1ODE1fQ" +
                    ".IVEjC7C3lH3B2FPrESFHzwJzZ_emWoQa52-3CqjH9Dc\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType =
                    "application/json", examples = {@ExampleObject(value = "{\"type\": \"invalid-query-parameter\", " +
                    "\"title\": \"Invalid query parameter(s)\", \"status\": 400, \"detail\": \"Password size must be " +
                    "between 8" +
                    " and 20\", \"instance\": \"/api/v1/auth/signup\"}")})),
    })
    ResponseEntity<JwtAuthenticationResponse> signup(@Valid @RequestBody SignUpReq request);

    @PostMapping("/signin")
    @Operation(summary = "Signin with an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType =
                    "application/json", examples = {@ExampleObject(value = "{\"token\": \"eyJhbGciOiJIUzI1NiJ9" +
                    ".eyJzdWIiOiJtYXJjbzk5QGdtYWlsLmNvbSIsImlhdCI6MTcwMjQ4OTQxNSwiZXhwIjoxNzAyNTc1ODE1fQ" +
                    ".IVEjC7C3lH3B2FPrESFHzwJzZ_emWoQa52-3CqjH9Dc\"}")})),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType =
                    "application/json", examples = {@ExampleObject(value = "{\"type\": \"invalid-query-parameter\", " +
                    "\"title\": \"Invalid query parameter(s)\", \"status\": 400, \"detail\": \"Password size must be " +
                    "between 8" +
                    " and 20\", \"instance\": \"/api/v1/auth/signin\"}")})),
    })
    ResponseEntity<JwtAuthenticationResponse> signin(@Valid @RequestBody SignInReq request);
}
