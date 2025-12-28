package com.nikunj.codenex.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ResourceNotFoundException extends RuntimeException {
    
    String resourceName;
    String resourceId;

    @Override
    public String getMessage() {
        if (resourceName != null && resourceId != null) {
            return String.format("%s not found with id: %s", resourceName, resourceId);
        }
        return resourceName != null ? resourceName : "Resource not found";
    }
}
