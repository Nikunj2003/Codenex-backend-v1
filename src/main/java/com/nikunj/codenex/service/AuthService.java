package com.nikunj.codenex.service;

import com.nikunj.codenex.dto.auth.request.LoginRequest;
import com.nikunj.codenex.dto.auth.request.SignupRequest;
import com.nikunj.codenex.dto.auth.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
