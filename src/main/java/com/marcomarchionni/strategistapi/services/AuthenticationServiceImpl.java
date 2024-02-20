package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.SignInReq;
import com.marcomarchionni.strategistapi.dtos.request.SignUpReq;
import com.marcomarchionni.strategistapi.dtos.response.auth.JwtAuthenticationResponse;
import com.marcomarchionni.strategistapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final String adminKey;

    public AuthenticationServiceImpl(@Value("${signup.adminKey}") String adminKey,
                                     UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.adminKey = adminKey;
    }

    @Override
    public JwtAuthenticationResponse signUp(SignUpReq request) {
        var role = determineUserRole(request.getAdminKey());
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountId(request.getAccountId())
                .role(role).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInReq request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    private User.Role determineUserRole(String requestAdminKey) {
        return requestAdminKey != null && requestAdminKey.equals(adminKey) ? User.Role.ADMIN : User.Role.USER;
    }
}
