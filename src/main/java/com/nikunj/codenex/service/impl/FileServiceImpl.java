package com.nikunj.codenex.service.impl;

import com.nikunj.codenex.dto.project.response.FileContentResponse;
import com.nikunj.codenex.dto.project.response.FileTreeResponse;
import com.nikunj.codenex.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Override
    public List<FileTreeResponse> getFileContentTree(Long userId, Long projectId) {
        // TODO: Implement file tree retrieval logic
        return Collections.emptyList();
    }

    @Override
    public FileContentResponse getFileContent(Long userId, Long projectId, String path) {
        // TODO: Implement get file logic
        return null;
    }
}
