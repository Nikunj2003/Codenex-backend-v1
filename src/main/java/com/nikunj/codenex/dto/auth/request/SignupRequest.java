package com.nikunj.codenex.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
    @NotBlank
    @Size(min = 1, max = 30)
    String name,

    @Email
    @NotBlank
    String email,

    @Size(min = 4, max = 50)
    @NotBlank
    String password
) {

}
