package com.nikunj.codenex.dto.auth.request;

public record LoginRequest(
        String email,
        String password) {
}
