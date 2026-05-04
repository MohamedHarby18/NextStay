package com.nextstay.nextstaymain.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateReviewRequest {
    private UUID reservationId;
    private int rating; // Must be 1-5
    private String comment;
}