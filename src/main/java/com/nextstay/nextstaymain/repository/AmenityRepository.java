package com.nextstay.nextstaymain.repository;

import com.nextstay.nextstaymain.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AmenityRepository extends JpaRepository<Amenity, UUID> {

}