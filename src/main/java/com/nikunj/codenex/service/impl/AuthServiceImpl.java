package com.nikunj.codenex.service.impl;

import org.springframework.stereotype.Service;

import com.nikunj.codenex.dto.auth.request.LoginRequest;
import com.nikunj.codenex.dto.auth.request.SignupRequest;
import com.nikunj.codenex.dto.auth.response.AuthResponse;
import com.nikunj.codenex.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public AuthResponse signup(SignupRequest request) {
        // TODO: Implement signup logic
        return null;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // TODO: Implement login logic
        return null;
    }

}
