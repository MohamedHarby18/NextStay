package com.nextstay.nextstaymain.repository;

import com.nextstay.nextstaymain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

}