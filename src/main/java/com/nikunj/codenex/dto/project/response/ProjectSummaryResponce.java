package com.nikunj.codenex.dto.project.response;

import java.time.Instant;

public record ProjectSummaryResponce(
    Long id,
    String name,
    Instant createdAt,
    Instant updatedAt    
) {

}
