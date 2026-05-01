package com.nextstay.nextstaymain.reviewservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nextstay.nextstaymain.reviewservice.entity.Review;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

}