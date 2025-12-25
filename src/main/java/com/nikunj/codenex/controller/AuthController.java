package com.nikunj.codenex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikunj.codenex.dto.auth.request.LoginRequest;
import com.nikunj.codenex.dto.auth.request.SignupRequest;
import com.nikunj.codenex.dto.auth.response.AuthResponse;
import com.nikunj.codenex.dto.auth.response.UserProfileResponse;
import com.nikunj.codenex.service.AuthService;
import com.nikunj.codenex.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getProfile() {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(userService.getProfile(userId));
    }
}
