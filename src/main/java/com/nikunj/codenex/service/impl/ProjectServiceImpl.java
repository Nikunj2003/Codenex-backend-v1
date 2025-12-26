package com.nikunj.codenex.service.impl;

import org.springframework.stereotype.Service;

import com.nikunj.codenex.service.ProjectService;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import com.nikunj.codenex.dto.project.request.ProjectRequest;
import com.nikunj.codenex.dto.project.response.ProjectResponse;
import com.nikunj.codenex.dto.project.response.ProjectSummaryResponce;
import com.nikunj.codenex.entity.Project;
import com.nikunj.codenex.entity.User;
import com.nikunj.codenex.mapper.ProjectMapper;
import com.nikunj.codenex.repository.ProjectRepository;
import com.nikunj.codenex.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;

    @Override
    public ProjectResponse createProject(Long userId, ProjectRequest request) {
        User owner = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Project project = Project.builder()
                .name(request.name())
                .owner(owner)
                .isPublic(false)
                .build();

        project = projectRepository.save(project);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public List<ProjectSummaryResponce> getUserProjects(Long userId) {
        return projectMapper.toProjectSummaryResponceList(projectRepository.findAllAccessibleByUser(userId));
    }

    @Override
    public ProjectResponse getUserProjectById(Long userId, Long projectId) {
        Project project = getAccessibleProjectById(userId, projectId);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse updateProject(Long userId, Long projectId, ProjectRequest request) {
        Project project = getAccessibleProjectById(userId, projectId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("User is not authorized to update this project");
        }

        project.setName(request.name());

        project = projectRepository.save(project);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void softDelete(Long userId, Long projectId) {
        Project project = getAccessibleProjectById(userId, projectId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("User is not authorized to delete this project");
        }

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    private Project getAccessibleProjectById(Long userId, Long projectId) {
        return projectRepository.findAccessibleProjectById(userId, projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }
}
