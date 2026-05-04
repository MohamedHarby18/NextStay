package com.nextstay.notificationservice.controller;

import com.nextstay.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotificationController {

    @PostMapping("/email")
    public ResponseEntity<ApiResponse<String>> sendEmail(@RequestBody Object emailRequest) {
        // Implement email sending logic
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .statusCode(200)
                .message("Email sent successfully")
                .data("Email ID: 12345")
                .success(true)
                .build());
    }

    @PostMapping("/sms")
    public ResponseEntity<ApiResponse<String>> sendSMS(@RequestBody Object smsRequest) {
        // Implement SMS sending logic
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .statusCode(200)
                .message("SMS sent successfully")
                .data("SMS ID: 12345")
                .success(true)
                .build());
    }
}
