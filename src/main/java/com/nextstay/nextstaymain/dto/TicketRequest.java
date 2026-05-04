package com.nextstay.nextstaymain.dto;

import com.nextstay.nextstaymain.entity.SupportTicket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketRequest {
    @NotBlank
    private String subject;
    @NotBlank
    private String description;
    @NotNull
    private SupportTicket.TicketCategory category; // billing, booking, listing, other
}