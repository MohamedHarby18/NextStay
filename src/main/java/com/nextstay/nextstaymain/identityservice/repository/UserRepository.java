package com.nextstay.nextstaymain.identityservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nextstay.nextstaymain.identityservice.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
