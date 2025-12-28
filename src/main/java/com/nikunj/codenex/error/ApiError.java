package com.nikunj.codenex.error;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Standard API error response format.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard error response")
public record ApiError(
    @Schema(description = "HTTP status code", example = "400")
    int status,
    
    @Schema(description = "Error type/code", example = "Bad Request")
    String error,
    
    @Schema(description = "Human-readable error message", example = "Invalid request parameters")
    String message,
    
    @Schema(description = "Request path that caused the error", example = "/api/projects/1")
    String path,
    
    @Schema(description = "Timestamp when the error occurred", example = "2024-01-15T10:30:00Z")
    Instant timestamp,
    
    @Schema(description = "Validation errors for specific fields (only present for validation failures)")
    Map<String, List<String>> validationErrors
) {
    /**
     * Constructor for simple errors without validation details.
     */
    public ApiError(int status, String error, String message, String path) {
        this(status, error, message, path, Instant.now(), null);
    }

    /**
     * Constructor for validation errors with field-specific messages.
     */
    public ApiError(int status, String error, String message, String path, Map<String, List<String>> validationErrors) {
        this(status, error, message, path, Instant.now(), validationErrors);
    }
}
