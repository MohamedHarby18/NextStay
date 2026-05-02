package com.nextstay.nextstaymain.common.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice // Tells Spring: "Listen for errors across ALL Controllers"
public class GlobalExceptionHandler {

    // 1. Handling "User Not Found" (From JpaUserDetailsService)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Not Found", "message", ex.getMessage())
        );
    }

    // 2. Handling "Wrong Password" (Thrown automatically by AuthenticationManager)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error", "Unauthorized", "message", "Invalid email or password")
        );
    }

    // 3. Handling General Logic Errors (e.g., "Email already exists" from UserService)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleGeneralExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error", "Bad Request", "message", ex.getMessage())
        );
    }

    //4. handling locked account errors
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<Object> handleLockedAccount(LockedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            Map.of("error", "Locked", "message", "Your account has been banned.")
        );
    }
}