package com.nextstay.bookingservice.service;

import com.nextstay.bookingservice.entity.Booking;
import com.nextstay.bookingservice.repository.BookingRepository;
import com.nextstay.bookingservice.dto.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = Booking.builder()
                .guestId(bookingDTO.getGuestId())
                .propertyId(bookingDTO.getPropertyId())
                .checkInDate(bookingDTO.getCheckInDate())
                .checkOutDate(bookingDTO.getCheckOutDate())
                .numberOfGuests(bookingDTO.getNumberOfGuests())
                .totalPrice(bookingDTO.getTotalPrice())
                .status(Booking.BookingStatus.PENDING)
                .specialRequests(bookingDTO.getSpecialRequests())
                .build();

        Booking savedBooking = bookingRepository.save(booking);
        return mapToDTO(savedBooking);
    }

    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return mapToDTO(booking);
    }

    public List<BookingDTO> getBookingsByGuestId(Long guestId) {
        return bookingRepository.findByGuestId(guestId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<BookingDTO> getBookingsByPropertyId(Long propertyId) {
        return bookingRepository.findByPropertyId(propertyId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO confirmBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        Booking updated = bookingRepository.save(booking);
        return mapToDTO(updated);
    }

    public BookingDTO cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        Booking updated = bookingRepository.save(booking);
        return mapToDTO(updated);
    }

    private BookingDTO mapToDTO(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .guestId(booking.getGuestId())
                .propertyId(booking.getPropertyId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .numberOfGuests(booking.getNumberOfGuests())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus().toString())
                .specialRequests(booking.getSpecialRequests())
                .build();
    }
}
