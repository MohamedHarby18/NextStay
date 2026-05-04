package com.nextstay.paymentservice.controller;

import com.nextstay.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {

    @PostMapping
    public ResponseEntity<ApiResponse<String>> processPayment(@RequestBody Object paymentRequest) {
        // Implement payment processing logic
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .statusCode(200)
                .message("Payment processed successfully")
                .data("Payment ID: 12345")
                .success(true)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> getPaymentStatus(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .statusCode(200)
                .message("Payment status retrieved")
                .data("COMPLETED")
                .success(true)
                .build());
    }
}
