package com.nikunj.codenex.dto.member.response;

import com.nikunj.codenex.enums.ProjectRole;
import java.time.Instant;

public record PendingInviteResponse(
        Long projectId,
        String projectName,
        Long ownerId,
        String ownerName,
        String ownerEmail,
        ProjectRole role,
        Instant invitedAt) {
}
