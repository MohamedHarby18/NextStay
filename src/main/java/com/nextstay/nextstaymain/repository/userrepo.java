package com.nextstay.nextstaymain.repository;
import com.nextstay.nextstaymain.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userrepo extends JpaRepository<user,Long> {

}
