package com.nextstay.nextstaymain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAgentRequest {
    private String email;
    private String name;
    private String passwordHash;
    private String role;
}
