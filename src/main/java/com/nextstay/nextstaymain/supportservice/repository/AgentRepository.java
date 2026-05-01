package com.nextstay.nextstaymain.supportservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nextstay.nextstaymain.supportservice.entity.Agent;

import java.util.UUID;

public interface AgentRepository extends JpaRepository<Agent, UUID> {

}
