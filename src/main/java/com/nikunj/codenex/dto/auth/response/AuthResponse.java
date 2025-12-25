package com.nikunj.codenex.dto.auth.response;

public record AuthResponse(
    String token, 
    UserProfileResponse user) {

}
