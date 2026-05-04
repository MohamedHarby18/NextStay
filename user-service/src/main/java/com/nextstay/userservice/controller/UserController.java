package com.nextstay.userservice.controller;

import com.nextstay.userservice.service.UserService;
import com.nextstay.userservice.dto.UserRegistrationRequest;
import com.nextstay.common.dto.UserDTO;
import com.nextstay.common.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@RequestBody UserRegistrationRequest request) {
        UserDTO user = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserDTO>builder()
                        .statusCode(201)
                        .message("User registered successfully")
                        .data(user)
                        .success(true)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.<UserDTO>builder()
                .statusCode(200)
                .message("User retrieved successfully")
                .data(user)
                .success(true)
                .build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(ApiResponse.<UserDTO>builder()
                .statusCode(200)
                .message("User retrieved successfully")
                .data(user)
                .success(true)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.<List<UserDTO>>builder()
                .statusCode(200)
                .message("Users retrieved successfully")
                .data(users)
                .success(true)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO user = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(ApiResponse.<UserDTO>builder()
                .statusCode(200)
                .message("User updated successfully")
                .data(user)
                .success(true)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .statusCode(200)
                .message("User deleted successfully")
                .success(true)
                .build());
    }
}
