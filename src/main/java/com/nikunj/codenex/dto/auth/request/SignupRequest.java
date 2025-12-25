package com.nikunj.codenex.dto.auth.request;

public record SignupRequest(
    String name,
    String email,
    String password
) {

}
