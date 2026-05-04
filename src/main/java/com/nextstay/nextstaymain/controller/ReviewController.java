package com.nextstay.nextstaymain.controller;

import com.nextstay.nextstaymain.dto.CreateReviewRequest;
import com.nextstay.nextstaymain.entity.Review;
import com.nextstay.nextstaymain.entity.User;
import com.nextstay.nextstaymain.service.ReviewService;
import com.nextstay.nextstaymain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Review> submitReview(@RequestBody CreateReviewRequest request, Authentication authentication) {
        // Fetch the currently authenticated user
        User guest = userService.findByEmail(authentication.getName()); 
        Review savedReview = reviewService.submitReview(guest, request);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<Review>> getListingReviews(@PathVariable UUID listingId) {
        return ResponseEntity.ok(reviewService.getListingReviews(listingId));
    }
}