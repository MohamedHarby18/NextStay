package com.nextstay.nextstaymain.service;

import com.nextstay.nextstaymain.dto.CreateTicketRequest;
import com.nextstay.nextstaymain.entity.SupportTicket;
import com.nextstay.nextstaymain.entity.TicketMessage;
import com.nextstay.nextstaymain.entity.User;
import com.nextstay.nextstaymain.repository.SupportTicketRepository;
import com.nextstay.nextstaymain.repository.TicketMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SupportService {

    @Autowired
    private SupportTicketRepository ticketRepository;
    @Autowired
    private TicketMessageRepository messageRepository;

    public SupportTicket createTicket(User user, CreateTicketRequest request) {
        SupportTicket ticket = SupportTicket.builder()
                .user(user)
                .userRole(user.getRole())
                .subject(request.getSubject())
                .description(request.getDescription())
                .category(request.getCategory())
                .status(SupportTicket.TicketStatus.open)
                .build();
        return ticketRepository.save(ticket);
    }

    public TicketMessage replyToTicket(UUID ticketId, User sender, String messageText) {
        SupportTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        TicketMessage message = TicketMessage.builder()
                .ticket(ticket)
                .sender(sender)
                .senderRole(TicketMessage.SenderRole.valueOf(sender.getRole().name()))
                .messageText(messageText)
                .build();

        // If an admin/agent replies, update status to in_progress
        if (sender.getRole() == User.UserRole.admin && ticket.getStatus() == SupportTicket.TicketStatus.open) {
            ticket.setStatus(SupportTicket.TicketStatus.in_progress);
            ticketRepository.save(ticket);
        }

        return messageRepository.save(message);
    }

    public List<SupportTicket> getUserTickets(User user) {
        return ticketRepository.findByUserId(user.getId()); 
    }
}