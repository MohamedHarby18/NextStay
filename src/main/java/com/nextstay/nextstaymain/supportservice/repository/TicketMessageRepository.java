package com.nextstay.nextstaymain.supportservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nextstay.nextstaymain.supportservice.entity.TicketMessage;

import java.util.UUID;

public interface TicketMessageRepository extends JpaRepository<TicketMessage, UUID> {

}