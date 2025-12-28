package com.nikunj.codenex.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Email
        @NotBlank
        String email,

        @Size(min = 4, max = 50)
        @NotBlank
        String password) {
}
