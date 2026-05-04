package com.nextstay.nextstaymain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HostResponseRequest {
    @NotBlank(message = "Response cannot be empty")
    private String responseText;
}