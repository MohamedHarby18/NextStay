package com.nextstay.nextstaymain.repository;

import com.nextstay.nextstaymain.entity.HostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface HostResponseRepository extends JpaRepository<HostResponse, UUID> {
    // Find a host's response to a specific review
    Optional<HostResponse> findByReviewId(UUID reviewId);
}