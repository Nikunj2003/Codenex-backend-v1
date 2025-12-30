package com.nikunj.codenex.dto.member.request;

import jakarta.validation.constraints.NotNull;

public record TransferOwnershipRequest(
    @NotNull
    Long newOwnerId) {
}
