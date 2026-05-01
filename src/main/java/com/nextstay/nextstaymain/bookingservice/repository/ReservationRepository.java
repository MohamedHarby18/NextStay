package com.nextstay.nextstaymain.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nextstay.nextstaymain.bookingservice.entity.Reservation;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

}