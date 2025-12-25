package com.nikunj.codenex.dto.project.response;

import java.time.Instant;
import com.nikunj.codenex.dto.auth.response.UserProfileResponse;

public record ProjectResponse(
        Long id,
        String name,
        Boolean isPublic,
        Instant createdAt,
        Instant updatedAt,
        UserProfileResponse owner) {
}
