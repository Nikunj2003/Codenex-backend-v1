package com.nikunj.codenex.dto.subscription.request;

import jakarta.validation.constraints.NotBlank;

public record CheckoutRequest(
        @NotBlank
        String planId) {
}
