package com.nextstay.nextstaymain.listingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nextstay.nextstaymain.listingservice.entity.Amenity;

import java.util.UUID;

public interface AmenityRepository extends JpaRepository<Amenity, UUID> {

}