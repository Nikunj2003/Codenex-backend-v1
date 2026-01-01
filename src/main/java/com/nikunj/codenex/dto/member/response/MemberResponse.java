package com.nikunj.codenex.dto.member.response;

import com.nikunj.codenex.enums.ProjectRole;
import java.time.Instant;

public record MemberResponse(
        Long userId,
        String username,
        String name,
        ProjectRole projectRole,
        Instant invitedAt,
        Instant acceptedAt,
        Boolean isAccepted) {
}
