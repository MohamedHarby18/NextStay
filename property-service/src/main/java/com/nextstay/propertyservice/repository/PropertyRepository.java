package com.nextstay.propertyservice.repository;

import com.nextstay.propertyservice.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByHostId(Long hostId);

    List<Property> findByAvailable(boolean available);

    List<Property> findByCity(String city);
}
