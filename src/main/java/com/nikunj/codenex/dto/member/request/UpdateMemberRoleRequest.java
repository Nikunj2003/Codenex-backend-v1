package com.nikunj.codenex.dto.member.request;

import com.nikunj.codenex.enums.ProjectRole;

import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(@NotNull ProjectRole role) {
}
