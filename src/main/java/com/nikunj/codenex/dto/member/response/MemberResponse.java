package com.nikunj.codenex.dto.member.response;

import com.nikunj.codenex.enums.ProjectRole;
import java.time.Instant;

public record MemberResponse(
        Long id,
        String email,
        String name,
        String avatarUrl,
        ProjectRole role,
        Instant invitedAt) {
}
