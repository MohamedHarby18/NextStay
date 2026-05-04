package com.nextstay.nextstaymain.controller;

import com.nextstay.nextstaymain.dto.CreateTicketRequest;
import com.nextstay.nextstaymain.dto.TicketMessageRequest;
import com.nextstay.nextstaymain.entity.SupportTicket;
import com.nextstay.nextstaymain.entity.TicketMessage;
import com.nextstay.nextstaymain.entity.User;
import com.nextstay.nextstaymain.service.SupportService;
import com.nextstay.nextstaymain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    @Autowired
    private SupportService supportService;
    @Autowired
    private UserService userService;

    @PostMapping("/tickets")
    public ResponseEntity<SupportTicket> createTicket(@RequestBody CreateTicketRequest request, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok(supportService.createTicket(user, request));
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<SupportTicket>> getMyTickets(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok(supportService.getUserTickets(user));
    }

    @PostMapping("/tickets/{ticketId}/messages")
    public ResponseEntity<TicketMessage> replyToTicket(@PathVariable UUID ticketId, 
                                                       @RequestBody TicketMessageRequest request, 
                                                       Authentication authentication) {
        User sender = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok(supportService.replyToTicket(ticketId, sender, request.getMessageText()));
    }
}