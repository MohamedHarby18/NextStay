package com.nextstay.nextstaymain.repository;

import com.nextstay.nextstaymain.entity.AvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, UUID> {

}