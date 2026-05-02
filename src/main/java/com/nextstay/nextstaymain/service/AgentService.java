package com.nextstay.nextstaymain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nextstay.nextstaymain.dto.CreateAgentRequest;
import com.nextstay.nextstaymain.entity.Agent;
import com.nextstay.nextstaymain.repository.AgentRepository;

@Service
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Agent createAgent(CreateAgentRequest request) {
        Agent agent = Agent.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPasswordHash()))
                .role(Agent.AgentRole.valueOf(request.getRole() != null ? request.getRole() : "support_agent"))
                .isActive(true)
                .build();

        return agentRepository.save(agent);
    }
}
