package com.nextstay.nextstaymain.listingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nextstay.nextstaymain.listingservice.entity.Listing;

import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, UUID> {

}