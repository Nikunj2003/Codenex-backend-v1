package com.nikunj.codenex.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Exception thrown when a user attempts to access a resource they don't have permission to access.
 * Maps to HTTP 403 Forbidden.
 */
@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ForbiddenException extends RuntimeException {

    String errorMessage;

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
