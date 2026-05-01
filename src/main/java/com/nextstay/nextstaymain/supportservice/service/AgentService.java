package com.nextstay.nextstaymain.supportservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextstay.nextstaymain.identityservice.dto.CreateAgentRequest;
import com.nextstay.nextstaymain.supportservice.entity.Agent;
import com.nextstay.nextstaymain.supportservice.repository.AgentRepository;

@Service
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;

    public Agent createAgent(CreateAgentRequest request) {
        Agent agent = Agent.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(request.getPasswordHash())
                .role(Agent.AgentRole.valueOf(request.getRole() != null ? request.getRole() : "support_agent"))
                .isActive(true)
                .build();
        
        return agentRepository.save(agent);
    }
}
