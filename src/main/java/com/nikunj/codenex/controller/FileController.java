package com.nikunj.codenex.controller;

import com.nikunj.codenex.dto.project.response.FileContentResponse;
import com.nikunj.codenex.dto.project.response.FileTreeResponse;
import com.nikunj.codenex.service.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/files")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FileController {

    FileService fileService;

    @GetMapping
    public ResponseEntity<List<FileTreeResponse>> getFileContentTree(@PathVariable Long projectId) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(fileService.getFileContentTree(userId, projectId));
    }

    @GetMapping("/{*path}")
    public ResponseEntity<FileContentResponse> getFileContent(@PathVariable Long projectId, @PathVariable String path) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(fileService.getFileContent(userId, projectId, path));
    }
}
