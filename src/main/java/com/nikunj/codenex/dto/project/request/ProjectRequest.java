package com.nikunj.codenex.dto.project.request;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequest(
    @NotBlank
    String name
) {

}
