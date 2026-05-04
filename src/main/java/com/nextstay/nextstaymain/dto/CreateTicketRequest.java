package com.nextstay.nextstaymain.dto;
import lombok.Data;
import com.nextstay.nextstaymain.entity.SupportTicket;

@Data
public class CreateTicketRequest {
    private String subject;
    private String description;
    private SupportTicket.TicketCategory category;
}