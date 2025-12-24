package com.courseregistration.service;

import com.courseregistration.dto.RegisterRequest;
import com.courseregistration.dto.LoginRequest;
import com.courseregistration.dto.LoginResponse;
import com.courseregistration.entity.User;
import com.courseregistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    public String register(RegisterRequest registerRequest) {
        // Check if user already exists
        Optional<User> existingUser = userRepository.findByUsername(registerRequest.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        
        // Create new user
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setEnabled(true);
        
        userRepository.save(user);
        return "User registered successfully";
    }
    
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        
        if (user.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }
        
        User foundUser = user.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), foundUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String token = jwtTokenProvider.generateToken(foundUser.getUsername());
        return new LoginResponse(token, "Login successful");
    }
}
