package com.nextstay.nextstaymain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nextstay.nextstaymain.dto.LoginRequest;
import com.nextstay.nextstaymain.dto.LoginResponse;
import com.nextstay.nextstaymain.entity.User;
import com.nextstay.nextstaymain.entity.Agent;
import com.nextstay.nextstaymain.repository.UserRepository;
import com.nextstay.nextstaymain.repository.AgentRepository;
import com.nextstay.nextstaymain.security.JwtUtils;
import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AgentRepository agentRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    public LoginResponse login(LoginRequest request) {
        if ("user".equalsIgnoreCase(request.getType())) {
            return loginUser(request);
        } else if ("agent".equalsIgnoreCase(request.getType())) {
            return loginAgent(request);
        } else {
            return LoginResponse.builder()
                    .message("Invalid user type. Must be 'user' or 'agent'")
                    .build();
        }
    }
    
    private LoginResponse loginUser(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                String token = jwtUtils.generateToken(
                    user.getEmail(),
                    user.getId().toString(),
                    user.getRole().toString(),
                    "user"
                );
                
                return LoginResponse.builder()
                        .token(token)
                        .type("user")
                        .userId(user.getId().toString())
                        .email(user.getEmail())
                        .role(user.getRole().toString())
                        .message("Login successful")
                        .build();
            }
        }
        
        return LoginResponse.builder()
                .message("Invalid email or password")
                .build();
    }
    
    private LoginResponse loginAgent(LoginRequest request) {
        Optional<Agent> agentOptional = agentRepository.findByEmail(request.getEmail());
        
        if (agentOptional.isPresent()) {
            Agent agent = agentOptional.get();
            if (!agent.getIsActive()) {
                return LoginResponse.builder()
                        .message("Agent account is deactivated")
                        .build();
            }
            
            if (passwordEncoder.matches(request.getPassword(), agent.getPasswordHash())) {
                String token = jwtUtils.generateToken(
                    agent.getEmail(),
                    agent.getId().toString(),
                    agent.getRole().toString(),
                    "agent"
                );
                
                return LoginResponse.builder()
                        .token(token)
                        .type("agent")
                        .userId(agent.getId().toString())
                        .email(agent.getEmail())
                        .role(agent.getRole().toString())
                        .message("Login successful")
                        .build();
            }
        }
        
        return LoginResponse.builder()
                .message("Invalid email or password")
                .build();
    }
}
