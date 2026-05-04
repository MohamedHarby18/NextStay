package com.nextstay.nextstaymain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nextstay.nextstaymain.dto.CreateUserRequest;
import com.nextstay.nextstaymain.entity.User;
import com.nextstay.nextstaymain.entity.User.UserRole;
import com.nextstay.nextstaymain.repository.UserRepository;
import com.nextstay.nextstaymain.dto.UpdateProfileRequest;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(User.UserRole.valueOf(request.getRole().toUpperCase() != null ? request.getRole() : "guest"))
                .isVerified(false)
                .build();

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateProfile(User user, UpdateProfileRequest request) {
            // Update Email if changed and unique
            if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
                if (userRepository.existsByEmailAndIdNot(request.getEmail(), user.getId())) {
                    throw new IllegalArgumentException("Email is already in use.");
                }
                user.setEmail(request.getEmail());
            }

        if (request.getName() != null) user.setName(request.getName());
        if (request.getPhoneNumber() != null) user.setPhoneNumber(request.getPhoneNumber());
        if (request.getBio() != null) user.setBio(request.getBio());
        if (request.getProfilePhoto() != null) user.setProfilePhoto(request.getProfilePhoto());

        // Update password if requested
        if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
            if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())) {
                throw new IllegalArgumentException("Incorrect old password.");
            }
            user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        }

        return userRepository.save(user);
    }
    
    
}
