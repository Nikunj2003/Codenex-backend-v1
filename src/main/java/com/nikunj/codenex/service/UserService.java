package com.nikunj.codenex.service;

import com.nikunj.codenex.dto.auth.response.UserProfileResponse;

public interface UserService {
    UserProfileResponse getProfile(Long userId);
}
