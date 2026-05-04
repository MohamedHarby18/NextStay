package com.nextstay.nextstaymain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.nextstay.nextstaymain.dto.CreateUserRequest;
import com.nextstay.nextstaymain.dto.UpdateProfileRequest;
import com.nextstay.nextstaymain.dto.CreateAgentRequest;
import com.nextstay.nextstaymain.entity.User;
import com.nextstay.nextstaymain.entity.Agent;
import com.nextstay.nextstaymain.service.UserService;

import jakarta.validation.Valid;

import com.nextstay.nextstaymain.service.AgentService;

@RestController
@RequestMapping("/api/users")
public class UserController{
    @Autowired
    private UserService userService;
    @Autowired
    private AgentService agentService;

    
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<User> createAdmin(@Valid @RequestBody CreateUserRequest request) {
        request.setRole("admin");
        User user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // @PostMapping("/create-agent")
    // public ResponseEntity<User> createAgent(@RequestBody CreateUserRequest request) {
    //     request.setRole("support_agent");
    //     User user = userService.createUser(request);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(user);
    // }

    
    @PostMapping("/create-agent")
    public ResponseEntity<Agent> createAgent(@Valid @RequestBody CreateAgentRequest request) {
        Agent agent = agentService.createAgent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(agent);
    }

    // @PostMapping("/create-support-agent")
    // public ResponseEntity<Agent> createSupportAgent(@RequestBody CreateAgentRequest request) {
    //     request.setRole("support_agent");
    //     Agent agent = agentService.createAgent(request);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(agent);
    // }

    //  @PreAuthorize("isAuthenticated()")
    // @GetMapping("/me")
    // public ResponseEntity<User> getProfile(Authentication authentication) {

    //     String email = authentication.getName();
    //     User user = userService.findByEmail(email);

    //     return ResponseEntity.ok(user);
    // }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/me")
    public ResponseEntity<User> updateProfile(@Valid @RequestBody UpdateProfileRequest request, Authentication authentication) {

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        return ResponseEntity.ok(userService.updateProfile(user, request));
    }
}
