package com.nextstay.nextstaymain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nextstay.nextstaymain.dto.LoginRequest;
import com.nextstay.nextstaymain.dto.LoginResponse;
import com.nextstay.nextstaymain.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        
        if (response.getToken() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
    @PostMapping("/login-user")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request) {
        request.setType("user");
        LoginResponse response = authService.login(request);
        
        if (response.getToken() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
    @PostMapping("/login-agent")
    public ResponseEntity<LoginResponse> loginAgent(@RequestBody LoginRequest request) {
        request.setType("agent");
        LoginResponse response = authService.login(request);
        
        if (response.getToken() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
