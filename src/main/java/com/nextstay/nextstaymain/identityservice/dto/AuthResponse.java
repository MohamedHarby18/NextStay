package com.nextstay.nextstaymain.identityservice.dto;

public record AuthResponse(
    String accessToken,
    String email,
    String role,
    long expiresAt
) {}