package com.nextstay.nextstaymain.repository;
import com.nextstay.nextstaymain.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AgentRepository extends JpaRepository<Agent, UUID> {

}
