package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Log the values before processing
        System.out.println("Before processing JSON data:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());

        userRepository.save(user);

        // Log the values after processing
        System.out.println("After processing JSON data:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            // User found, authentication successful
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("message", "Login successful");
            responseMap.put("userId", user.getId());
            return ResponseEntity.ok(responseMap);
        } else {
            // User not found or credentials incorrect
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", "Invalid username or password");
            return ResponseEntity.status(401).body(errorMap);
        }
    }
}