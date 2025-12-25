package com.nikunj.codenex.service;

import java.util.List;

import com.nikunj.codenex.dto.project.response.FileContentResponse;
import com.nikunj.codenex.dto.project.response.FileTreeResponse;

public interface FileService {
    List<FileTreeResponse> getFileContentTree(Long userId, Long projectId);

    FileContentResponse getFileContent(Long userId, Long projectId, String path);
}
