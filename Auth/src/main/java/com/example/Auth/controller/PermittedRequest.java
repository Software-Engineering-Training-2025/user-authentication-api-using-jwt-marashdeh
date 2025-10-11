package com.example.Auth.controller;


import com.example.Auth.dto.JwtResponse;
import com.example.Auth.dto.UserResponse;
import com.example.Auth.model.Users;
import com.example.Auth.services.RefreshTokenService;
import com.example.Auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permitted")
public class PermittedRequest {

    @Autowired
    private UserService userService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public UserResponse addUser(@RequestBody Users user) {
        Users createdUser = userService.register(user);
        return new UserResponse(createdUser.getUsername(), createdUser.getEmail());
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody Users user) {
        return userService.verify(user);
    }

    @PostMapping("/refreshToken")
    public JwtResponse generateNewToken(@RequestBody String token) {
        return refreshTokenService.generateNewToken(token);
    }
}