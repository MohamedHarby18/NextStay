package com.nextstay.nextstaymain.reviewservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nextstay.nextstaymain.reviewservice.entity.HostResponse;

import java.util.UUID;

public interface HostResponseRepository extends JpaRepository<HostResponse, UUID> {

}