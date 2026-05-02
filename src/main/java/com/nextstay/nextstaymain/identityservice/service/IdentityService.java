package com.nextstay.nextstaymain.identityservice.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import com.nextstay.nextstaymain.identityservice.dto.AuthResponse;
import com.nextstay.nextstaymain.identityservice.dto.LoginRequest;
import com.nextstay.nextstaymain.identityservice.dto.RegistrationRequest;
import com.nextstay.nextstaymain.identityservice.security.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdentityService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService; // We call UserService to save the data

    public String register(RegistrationRequest request) {
        // Logic: Check if exists -> delegate saving to UserService
        return userService.createUser(request);
    }

    public AuthResponse login(LoginRequest request) {
        // Trigger the "Invisible Checks" we talked about
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        String token = jwtProvider.generateToken(auth);
        var user = userService.getByEmail(request.getEmail());
        
        return new AuthResponse(token, user.getEmail(), user.getRole().name(), 3600000L);
    }
}