package com.nextstay.nextstaymain.repository;

import com.nextstay.nextstaymain.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, UUID> {

}