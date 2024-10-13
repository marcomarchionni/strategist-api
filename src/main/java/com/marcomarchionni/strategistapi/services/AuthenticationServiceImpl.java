package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.dtos.request.RefreshTokenRequest;
import com.marcomarchionni.strategistapi.dtos.request.SignInReq;
import com.marcomarchionni.strategistapi.dtos.request.SignUpReq;
import com.marcomarchionni.strategistapi.dtos.response.auth.RefreshTokenResponse;
import com.marcomarchionni.strategistapi.dtos.response.auth.SigninResponse;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.InvalidTokenException;
import com.marcomarchionni.strategistapi.mappers.UserMapper;
import com.marcomarchionni.strategistapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final UserDetailsService userDetailsService;

    @Override
    public void signUp(SignUpReq request) {
        var role = determineRole(request.getRole());
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountId(request.getAccountId())
                .role(role).build();
        userRepository.save(user);
    }

    @Override
    public SigninResponse signIn(SignInReq request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password")); //TODO: custom exception
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        var userSummary = userMapper.toUserSummary(user);
        return SigninResponse.builder().user(userSummary).accessToken(accessToken).refreshToken(refreshToken).build();
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        // Extract username from refresh token
        String username = jwtService.extractUserName(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Validate the refresh token
        if (jwtService.isRefreshTokenValid(refreshToken) && userDetails != null) {
            // Generate new tokens
            String newAccessToken = jwtService.generateAccessToken(userDetails);
            String newRefreshToken = jwtService.generateRefreshToken(userDetails);

            // Return the response with new tokens
            return RefreshTokenResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        } else {
            throw new InvalidTokenException("Invalid or expired refresh token");
        }
    }

    private User.Role determineRole(String requestedRole) {
        if (requestedRole == null) return User.Role.USER;
        if (requestedRole.equals("ADMIN")) return User.Role.ADMIN;
        else return User.Role.USER;
    }


}
