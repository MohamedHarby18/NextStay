package com.nextstay.nextstaymain.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nextstay.nextstaymain.identityservice.entity.User;
import java.util.UUID;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UUID> {

    // Used during Login for verification
    Optional<User> findByEmail(String email);
    // Used during Registration
    boolean existsByEmail(String email);
    // Used during user profile updates
    boolean existsByEmailAndIdNot(String email, UUID currentId);
}
