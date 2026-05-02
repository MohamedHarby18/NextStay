package com.nextstay.nextstaymain.identityservice.controller;

import com.nextstay.nextstaymain.identityservice.entity.User;
import com.nextstay.nextstaymain.identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // A protected "Check My Profile" endpoint
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        // Principal is a built-in Spring object that holds the logged-in user's email
        User user = userService.getByEmail(principal.getName());
        return ResponseEntity.ok(user);
    }
}