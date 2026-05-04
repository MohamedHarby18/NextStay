package com.nextstay.nextstaymain.service;

import com.nextstay.nextstaymain.dto.CreateReviewRequest;
import com.nextstay.nextstaymain.entity.Reservation;
import com.nextstay.nextstaymain.entity.Review;
import com.nextstay.nextstaymain.entity.User;
import com.nextstay.nextstaymain.repository.ReservationRepository;
import com.nextstay.nextstaymain.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public Review submitReview(User guest, CreateReviewRequest request) {
        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // Ensure the user submitting the review is the guest who booked it
        if (!reservation.getGuest().getId().equals(guest.getId())) {
            throw new SecurityException("You can only review your own reservations.");
        }

        // Ensure the reservation is completed (COMPLETED or CHECKED_OUT status)
        if (!"COMPLETED".equalsIgnoreCase(reservation.getStatus().name())) {
            throw new IllegalStateException("You can only leave a review after your stay is completed.");
        }

        // Prevent multiple reviews for the same reservation
        if (reviewRepository.existsByReservationId(reservation.getId())) {
            throw new IllegalStateException("You have already reviewed this reservation.");
        }

        // Validate rating
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        Review review = Review.builder()
                .reservation(reservation)
                .guest(guest)
                .listing(reservation.getListing())
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        return reviewRepository.save(review);
    }

    public List<Review> getListingReviews(UUID listingId) {
        return reviewRepository.findByListingId(listingId); // Assuming this method exists in your repo
    }
}