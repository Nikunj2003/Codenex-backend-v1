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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.nikunj.codenex.dto.project.response.ProjectSummaryResponce;
import com.nikunj.codenex.dto.project.request.ProjectRequest;
import com.nikunj.codenex.dto.project.response.ProjectResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "Projects", description = "Project management endpoints")
public class ProjectController {
    ProjectService projectService;

    @Operation(summary = "Get all projects", description = "Retrieve all projects accessible by the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved projects"),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError")))
    })
    @GetMapping
    public ResponseEntity<List<ProjectSummaryResponce>> getMyProjects() {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(projectService.getUserProjects(userId));
    }

    @Operation(summary = "Get project by ID", description = "Retrieve a specific project by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved project"),
        @ApiResponse(responseCode = "404", description = "Project not found",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError")))
    })
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long projectId) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(projectService.getUserProjectById(userId, projectId));
    }

    @Operation(summary = "Create a new project", description = "Create a new project for the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Project created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ValidationApiError"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError")))
    })
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(userId, request));
    }

    @Operation(summary = "Update a project", description = "Update an existing project (owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project updated successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ValidationApiError"))),
        @ApiResponse(responseCode = "403", description = "Not authorized to update this project",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "404", description = "Project not found",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError")))
    })
    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long projectId,
            @Valid @RequestBody ProjectRequest request) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(projectService.updateProject(userId, projectId, request));
    }

    @Operation(summary = "Delete a project", description = "Soft delete a project (owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Project deleted successfully"),
        @ApiResponse(responseCode = "403", description = "Not authorized to delete this project",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "404", description = "Project not found",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError")))
    })
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        projectService.softDelete(userId, projectId);
        return ResponseEntity.noContent().build();
    }

}
