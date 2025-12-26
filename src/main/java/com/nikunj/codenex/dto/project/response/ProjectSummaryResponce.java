package com.nikunj.codenex.dto.project.response;

import java.time.Instant;

public record ProjectSummaryResponce(
    Long id,
    String projectName,
    Instant createdAt,
    Instant updatedAt    
) {

}
