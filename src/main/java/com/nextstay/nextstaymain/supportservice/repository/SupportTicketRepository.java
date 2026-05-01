package com.nextstay.nextstaymain.supportservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nextstay.nextstaymain.supportservice.entity.SupportTicket;

import java.util.UUID;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, UUID> {

}