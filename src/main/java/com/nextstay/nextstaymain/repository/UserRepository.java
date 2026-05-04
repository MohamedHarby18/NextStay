package com.nextstay.nextstaymain.repository;
import com.nextstay.nextstaymain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    // Used during Registration
    boolean existsByEmail(String email);
    // Used during user profile updates
    boolean existsByEmailAndIdNot(String email, UUID currentId);
}
