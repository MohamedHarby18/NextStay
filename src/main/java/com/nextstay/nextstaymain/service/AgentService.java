package com.nextstay.nextstaymain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nextstay.nextstaymain.dto.CreateAgentRequest;
import com.nextstay.nextstaymain.dto.UpdateProfileRequest;
import com.nextstay.nextstaymain.entity.Agent;
import com.nextstay.nextstaymain.repository.AgentRepository;

@Service
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Agent createAgent(CreateAgentRequest request) {
        if (agentRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        Agent agent = Agent.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Agent.AgentRole.valueOf(request.getRole() != null ? request.getRole() : "support_agent"))
                .isActive(true)
                .build();

        return agentRepository.save(agent);
    }
       public Agent updateProfile(Agent agent, UpdateProfileRequest request) {
            // Update Email if changed and unique
            if (request.getEmail() != null && !request.getEmail().equals(agent.getEmail())) {
                if (agentRepository.existsByEmailAndIdNot(request.getEmail(), agent.getId())) {
                    throw new IllegalArgumentException("Email is already in use.");
                }
                agent.setEmail(request.getEmail());
            }

        if (request.getName() != null) agent.setName(request.getName());
        if (request.getPhoneNumber() != null) agent.setPhoneNumber(request.getPhoneNumber());
        if (request.getBio() != null) agent.setBio(request.getBio());
        if (request.getProfilePhoto() != null) agent.setProfilePhoto(request.getProfilePhoto());

        // Update password if requested
        if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
            if (!passwordEncoder.matches(request.getOldPassword(), agent.getPasswordHash())) {
                throw new IllegalArgumentException("Incorrect old password.");
            }
            agent.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        }

        return agentRepository.save(agent);
    }
    
  
}
