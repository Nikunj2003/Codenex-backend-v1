package com.nikunj.codenex.service.impl;

import org.springframework.stereotype.Service;

import com.nikunj.codenex.service.ProjectService;
import com.nikunj.codenex.enums.ProjectRole;
import com.nikunj.codenex.error.ForbiddenException;
import com.nikunj.codenex.error.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import com.nikunj.codenex.dto.project.request.ProjectRequest;
import com.nikunj.codenex.dto.project.response.ProjectResponse;
import com.nikunj.codenex.dto.project.response.ProjectSummaryResponce;
import com.nikunj.codenex.entity.Project;
import com.nikunj.codenex.entity.ProjectMember;
import com.nikunj.codenex.entity.ProjectMemberId;
import com.nikunj.codenex.entity.User;
import com.nikunj.codenex.mapper.ProjectMapper;
import com.nikunj.codenex.repository.ProjectMemberRepository;
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
    ProjectMemberRepository projectMemberRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;

    @Override
    public ProjectResponse createProject(Long userId, ProjectRequest request) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));

        Project project = Project.builder()
                .name(request.name())
                .isPublic(false)
                .build();

        project = projectRepository.save(project);

        // Create OWNER membership
        ProjectMember ownerMember = ProjectMember.builder()
                .id(new ProjectMemberId(project.getId(), userId))
                .project(project)
                .user(owner)
                .projectRole(ProjectRole.OWNER)
                .invitedAt(Instant.now())
                .acceptedAt(Instant.now())
                .build();

        projectMemberRepository.save(ownerMember);

        return projectMapper.toProjectResponse(project, owner);
    }

    @Override
    public List<ProjectSummaryResponce> getUserProjects(Long userId) {
        return projectMapper.toProjectSummaryResponceList(projectRepository.findAllAccessibleByUser(userId));
    }

    @Override
    public ProjectResponse getUserProjectById(Long userId, Long projectId) {
        Project project = getAccessibleProjectById(userId, projectId);
        User owner = getProjectOwner(projectId);
        return projectMapper.toProjectResponse(project, owner);
    }

    @Override
    public ProjectResponse updateProject(Long userId, Long projectId, ProjectRequest request) {
        Project project = getAccessibleProjectById(userId, projectId);

        if (!projectMemberRepository.isOwner(projectId, userId)) {
            throw new ForbiddenException("You are not authorized to update this project");
        }

        project.setName(request.name());

        project = projectRepository.save(project);

        User owner = getProjectOwner(projectId);
        return projectMapper.toProjectResponse(project, owner);
    }

    @Override
    public void softDelete(Long userId, Long projectId) {
        Project project = getAccessibleProjectById(userId, projectId);

        if (!projectMemberRepository.isOwner(projectId, userId)) {
            throw new ForbiddenException("You are not authorized to delete this project");
        }

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    private Project getAccessibleProjectById(Long userId, Long projectId) {
        return projectRepository.findAccessibleProjectById(userId, projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));
    }

    private User getProjectOwner(Long projectId) {
        return projectMemberRepository.findOwnerByProjectId(projectId)
                .map(ProjectMember::getUser)
                .orElseThrow(() -> new ResourceNotFoundException("Project owner", projectId.toString()));
    }
}
