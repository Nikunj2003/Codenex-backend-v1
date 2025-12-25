package com.nikunj.codenex.service;

import com.nikunj.codenex.dto.project.response.ProjectSummaryResponce;
import com.nikunj.codenex.dto.project.request.ProjectRequest;
import com.nikunj.codenex.dto.project.response.ProjectResponse;
import java.util.List;

public interface ProjectService {
    List<ProjectSummaryResponce> getUserProjects(Long userId);

    ProjectResponse getUserProjectById(Long userId, Long projectId);

    ProjectResponse createProject(Long userId, ProjectRequest request);

    ProjectResponse updateProject(Long userId, Long projectId, ProjectRequest request);

    void softDelete(Long userId, Long projectId);
}
