package com.nextstay.nextstaymain.repository;
import com.nextstay.nextstaymain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
