package com.nikunj.codenex.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Exception thrown when a resource conflict occurs (e.g., duplicate entry).
 * Maps to HTTP 409 Conflict.
 */
@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ConflictException extends RuntimeException {

    String errorMessage;

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
