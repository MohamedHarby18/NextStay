package com.nextstay.bookingservice.controller;

import com.nextstay.bookingservice.service.BookingService;
import com.nextstay.bookingservice.dto.BookingDTO;
import com.nextstay.common.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookingDTO>> createBooking(@RequestBody BookingDTO bookingDTO) {
        BookingDTO booking = bookingService.createBooking(bookingDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<BookingDTO>builder()
                        .statusCode(201)
                        .message("Booking created successfully")
                        .data(booking)
                        .success(true)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingDTO>> getBookingById(@PathVariable Long id) {
        BookingDTO booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(ApiResponse.<BookingDTO>builder()
                .statusCode(200)
                .message("Booking retrieved successfully")
                .data(booking)
                .success(true)
                .build());
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getBookingsByGuestId(@PathVariable Long guestId) {
        List<BookingDTO> bookings = bookingService.getBookingsByGuestId(guestId);
        return ResponseEntity.ok(ApiResponse.<List<BookingDTO>>builder()
                .statusCode(200)
                .message("Bookings retrieved successfully")
                .data(bookings)
                .success(true)
                .build());
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getBookingsByPropertyId(@PathVariable Long propertyId) {
        List<BookingDTO> bookings = bookingService.getBookingsByPropertyId(propertyId);
        return ResponseEntity.ok(ApiResponse.<List<BookingDTO>>builder()
                .statusCode(200)
                .message("Bookings retrieved successfully")
                .data(bookings)
                .success(true)
                .build());
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<BookingDTO>> confirmBooking(@PathVariable Long id) {
        BookingDTO booking = bookingService.confirmBooking(id);
        return ResponseEntity.ok(ApiResponse.<BookingDTO>builder()
                .statusCode(200)
                .message("Booking confirmed successfully")
                .data(booking)
                .success(true)
                .build());
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<BookingDTO>> cancelBooking(@PathVariable Long id) {
        BookingDTO booking = bookingService.cancelBooking(id);
        return ResponseEntity.ok(ApiResponse.<BookingDTO>builder()
                .statusCode(200)
                .message("Booking cancelled successfully")
                .data(booking)
                .success(true)
                .build());
    }
}
