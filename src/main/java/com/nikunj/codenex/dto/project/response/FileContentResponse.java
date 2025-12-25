package com.nikunj.codenex.dto.project.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileContentResponse {
    private String content;
    private String path;
}
