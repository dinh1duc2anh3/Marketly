package com.darian.ecommerce.controller;

import com.darian.ecommerce.dto.UserDTO;
import com.darian.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // User login (can be implemented later for JWT auth)
    @PostMapping("/login")
    public ResponseEntity<String> login() {
        // Implement your login logic here
        return ResponseEntity.ok("Login successful");
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        userService.register(userDTO);
        return ResponseEntity.ok("User registered successfully");
    }
}
