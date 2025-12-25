package com.nikunj.codenex.service.impl;

import org.springframework.stereotype.Service;

import com.nikunj.codenex.service.ProjectService;

import lombok.RequiredArgsConstructor;

import com.nikunj.codenex.dto.project.request.ProjectRequest;
import com.nikunj.codenex.dto.project.response.ProjectResponse;
import com.nikunj.codenex.dto.project.response.ProjectSummaryResponce;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Override
    public List<ProjectSummaryResponce> getUserProjects(Long userId) {
        return Collections.emptyList();
    }

    @Override
    public ProjectResponse getUserProjectById(Long userId, Long projectId) {
        return null;
    }

    @Override
    public ProjectResponse createProject(Long userId, ProjectRequest request) {
        return null;
    }

    @Override
    public ProjectResponse updateProject(Long userId, Long projectId, ProjectRequest request) {
        return null;
    }

    @Override
    public void softDelete(Long userId, Long projectId) {

    }
}
