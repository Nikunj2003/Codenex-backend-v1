package com.nikunj.codenex.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikunj.codenex.service.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import com.nikunj.codenex.dto.project.response.ProjectSummaryResponce;
import com.nikunj.codenex.dto.project.request.ProjectRequest;
import com.nikunj.codenex.dto.project.response.ProjectResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectController {
    ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectSummaryResponce>> getMyProjects() {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(projectService.getUserProjects(userId));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long projectId) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(projectService.getUserProjectById(userId, projectId));
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(userId, request));
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long projectId,
            @Valid @RequestBody ProjectRequest request) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(projectService.updateProject(userId, projectId, request));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        projectService.softDelete(userId, projectId);
        return ResponseEntity.noContent().build();
    }

}
