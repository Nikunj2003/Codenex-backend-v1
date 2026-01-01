package com.nikunj.codenex.dto.member.request;

import com.nikunj.codenex.enums.ProjectRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InviteMemberRequest(
    @Email
    @NotBlank
    String username,

    @NotNull
    ProjectRole role) {
}
