package com.nikunj.codenex.service.impl;

import org.springframework.stereotype.Service;

import com.nikunj.codenex.service.ProjectService;
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
import com.nikunj.codenex.entity.User;
import com.nikunj.codenex.mapper.ProjectMapper;
import com.nikunj.codenex.repository.ProjectRepository;
import com.nikunj.codenex.repository.UserRepository;

import java.time.Instant;
import java.util.List;

import com.nikunj.codenex.entity.ProjectMember;
import com.nikunj.codenex.entity.ProjectMemberId;
import com.nikunj.codenex.enums.ProjectRole;
import com.nikunj.codenex.repository.ProjectMemberRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMemberRepository projectMemberRepository;
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

        ProjectMember ownerMember = ProjectMember.builder()
                .id(new ProjectMemberId(project.getId(), owner.getId()))
                .project(project)
                .user(owner)
                .projectRole(ProjectRole.OWNER)
                .invitedAt(Instant.now())
                .acceptedAt(Instant.now())
                .build();

        projectMemberRepository.save(ownerMember);

        return projectMapper.toProjectResponse(project, ownerMember);
    }

    @Override
    public List<ProjectSummaryResponce> getUserProjects(Long userId) {
        return projectMapper.toProjectSummaryResponceList(projectRepository.findAllAccessibleByUser(userId));
    }

    @Override
    public ProjectResponse getUserProjectById(Long userId, Long projectId) {
        Project project = getAccessibleProjectById(userId, projectId);
        ProjectMember ownerMember = projectMemberRepository.findOwnerByProjectId(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project Owner", projectId.toString()));
        return projectMapper.toProjectResponse(project, ownerMember);
    }

    @Override
    public ProjectResponse updateProject(Long userId, Long projectId, ProjectRequest request) {
        Project project = getAccessibleProjectById(userId, projectId);

        if (!isProjectOwner(userId, projectId)) {
            throw new ForbiddenException("You are not authorized to update this project");
        }

        project.setName(request.name());

        project = projectRepository.save(project);

        ProjectMember ownerMember = projectMemberRepository.findOwnerByProjectId(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project Owner", projectId.toString()));

        return projectMapper.toProjectResponse(project, ownerMember);
    }

    @Override
    public void softDelete(Long userId, Long projectId) {
        Project project = getAccessibleProjectById(userId, projectId);

        if (!isProjectOwner(userId, projectId)) {
            throw new ForbiddenException("You are not authorized to delete this project");
        }

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    private Project getAccessibleProjectById(Long userId, Long projectId) {
        return projectRepository.findAccessibleProjectById(userId, projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));
    }

    private boolean isProjectOwner(Long userId, Long projectId) {
        return projectMemberRepository.findOwnershipByProjectAndUser(projectId, userId).isPresent();
    }
}
