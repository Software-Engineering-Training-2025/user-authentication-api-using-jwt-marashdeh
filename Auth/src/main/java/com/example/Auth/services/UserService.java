package com.example.Auth.services;

import com.example.Auth.dto.JwtResponse;
import com.example.Auth.model.Users;
import com.example.Auth.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    JwtService jwtService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public Users register(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }


    public JwtResponse verify(Users user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return JwtResponse
                    .builder()
                    .accessToken(jwtService.generateToken(user.getUsername()))
                    .refreshToken(refreshTokenService.generateRefreshToken(user.getUsername()).getToken())
                    .build();
        }
        throw new IllegalStateException("username or password is incorrect ");
    }
}