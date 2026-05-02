package com.nextstay.nextstaymain.identityservice.controller;

import com.nextstay.nextstaymain.identityservice.dto.AuthResponse;
import com.nextstay.nextstaymain.identityservice.dto.LoginRequest;
import com.nextstay.nextstaymain.identityservice.dto.RegistrationRequest;
import com.nextstay.nextstaymain.identityservice.service.IdentityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IdentityService identityService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest request) {
        // The @Valid annotation triggers the @NotBlank/email checks in your DTO
        String result = identityService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        //returns JWT, Email, and Role as a JSON response
        AuthResponse response = identityService.login(request);
        return ResponseEntity.ok(response);
    }
}
