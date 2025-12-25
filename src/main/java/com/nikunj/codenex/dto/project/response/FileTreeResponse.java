package com.nikunj.codenex.dto.project.response;

import java.time.Instant;

public record FileTreeResponse(
        String path,
        String type,
        Instant modifiedAt,
        Long size) {
}
