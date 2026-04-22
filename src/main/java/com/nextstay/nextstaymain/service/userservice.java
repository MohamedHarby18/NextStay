package com.nextstay.nextstaymain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nextstay.nextstaymain.dto.CreateUserRequest;
import com.nextstay.nextstaymain.entity.User;
import com.nextstay.nextstaymain.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(CreateUserRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(request.getPasswordHash())
                .role(User.UserRole.valueOf(request.getRole() != null ? request.getRole() : "guest"))
                .isVerified(false)
                .build();

        return userRepository.save(user);
    }
}
