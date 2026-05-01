package com.nextstay.nextstaymain.identityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nextstay.nextstaymain.identityservice.dto.CreateAgentRequest;
import com.nextstay.nextstaymain.identityservice.dto.CreateUserRequest;
import com.nextstay.nextstaymain.identityservice.entity.User;
import com.nextstay.nextstaymain.identityservice.service.UserService;
import com.nextstay.nextstaymain.supportservice.entity.Agent;
import com.nextstay.nextstaymain.supportservice.service.AgentService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AgentService agentService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<User> createAdmin(@RequestBody CreateUserRequest request) {
        request.setRole("admin");
        User user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/create-agent")
    public ResponseEntity<Agent> createAgent(@RequestBody CreateAgentRequest request) {
        Agent agent = agentService.createAgent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(agent);
    }

    @PostMapping("/create-support-agent")
    public ResponseEntity<Agent> createSupportAgent(@RequestBody CreateAgentRequest request) {
        request.setRole("support_agent");
        Agent agent = agentService.createAgent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(agent);
    }
}
