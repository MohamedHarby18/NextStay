package com.nextstay.nextstaymain.repository;

import com.nextstay.nextstaymain.entity.TicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TicketMessageRepository extends JpaRepository<TicketMessage, UUID> {

}