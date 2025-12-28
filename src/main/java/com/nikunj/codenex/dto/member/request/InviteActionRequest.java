package com.nikunj.codenex.dto.member.request;

import com.nikunj.codenex.enums.InviteAction;

import jakarta.validation.constraints.NotNull;

public record InviteActionRequest(@NotNull InviteAction action) {
}
