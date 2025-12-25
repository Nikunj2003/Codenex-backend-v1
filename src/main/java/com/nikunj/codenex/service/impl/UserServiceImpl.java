package com.nikunj.codenex.service.impl;

import org.springframework.stereotype.Service;

import com.nikunj.codenex.dto.auth.response.UserProfileResponse;
import com.nikunj.codenex.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public UserProfileResponse getProfile(Long userId) {
        // TODO: Implement actual user retrieval logic
        return new UserProfileResponse(userId, "Test User", "test@example.com", null);
    }
}
