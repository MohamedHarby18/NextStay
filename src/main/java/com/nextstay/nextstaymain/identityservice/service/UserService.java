package com.nextstay.nextstaymain.identityservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nextstay.nextstaymain.identityservice.exception.UserNotFoundException;
import com.nextstay.nextstaymain.identityservice.dto.RegistrationRequest;
import com.nextstay.nextstaymain.identityservice.entity.User;
import com.nextstay.nextstaymain.identityservice.entity.UserRole;
import com.nextstay.nextstaymain.identityservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //modern way to handle autowired as well
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String createUser(RegistrationRequest request) {
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRole(UserRole.valueOf(request.getRole()));
        // The password hashing happens here, at the data level
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        
        userRepository.save(user);
        return "User created successfully";
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}