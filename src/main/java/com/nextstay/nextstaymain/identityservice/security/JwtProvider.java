package com.nextstay.nextstaymain.identityservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtEncoder jwtEncoder;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        
        // 1. Collect roles into a space-separated string (e.g., "ROLE_GUEST ROLE_ADMIN")
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        // 2. Build the "Payload" (The claims)
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("nextstay-identity-service") // Who sent it
                .issuedAt(now)                       // When
                .expiresAt(now.plus(1, ChronoUnit.HOURS)) // Valid for 1 hour
                .subject(authentication.getName())   // The user's email
                .claim("roles", scope)               // The custom "roles" claim
                .build();

        // 3. Scramble/Sign it with the secret key from SecurityConfig
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}