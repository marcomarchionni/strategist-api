package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.SignInReq;
import com.marcomarchionni.strategistapi.dtos.request.SignUpReq;
import com.marcomarchionni.strategistapi.dtos.response.auth.JwtAuthenticationResponse;
import com.marcomarchionni.strategistapi.dtos.response.auth.SigninResponse;
import com.marcomarchionni.strategistapi.mappers.UserMapper;
import com.marcomarchionni.strategistapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    public JwtAuthenticationResponse signUp(SignUpReq request) {
        var role = determineRole(request.getRole());
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
    public SigninResponse signIn(SignInReq request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password")); //TODO: custom exception
        var jwt = jwtService.generateToken(user);
        var userSummary = userMapper.toUserSummary(user);
        return SigninResponse.builder().user(userSummary).token(jwt).build();
    }

    private User.Role determineRole(String requestedRole) {
        if (requestedRole == null) return User.Role.USER;
        if (requestedRole.equals("ADMIN")) return User.Role.ADMIN;
        else return User.Role.USER;
    }


}
