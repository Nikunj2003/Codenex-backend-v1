package com.nikunj.codenex.service.impl;

import com.nikunj.codenex.dto.member.request.InviteMemberRequest;
import com.nikunj.codenex.dto.member.request.UpdateMemberRoleRequest;
import com.nikunj.codenex.dto.member.response.MemberResponse;
import com.nikunj.codenex.entity.Project;
import com.nikunj.codenex.entity.ProjectMember;
import com.nikunj.codenex.entity.ProjectMemberId;
import com.nikunj.codenex.entity.User;
import com.nikunj.codenex.enums.ProjectRole;
import com.nikunj.codenex.mapper.ProjectMemberMapper;
import com.nikunj.codenex.repository.ProjectMemberRepository;
import com.nikunj.codenex.repository.ProjectRepository;
import com.nikunj.codenex.repository.UserRepository;
import com.nikunj.codenex.service.ProjectMemberService;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectMemberRepository projectMemberRepository;
    ProjectMemberMapper projectMemberMapper;
    ProjectRepository projectRepository;
    UserRepository userRepository;

    @Override
    public List<MemberResponse> getProjectMembers(Long userId, Long projectId) {
        Project project = getAccessibleProjectById(userId, projectId);

        List<MemberResponse> memberResponseList = new ArrayList<>();

        memberResponseList.add(projectMemberMapper.toMemberResponseFromOwner(project.getOwner()));

        memberResponseList.addAll(projectMemberRepository.findByProjectId(projectId).stream()
                .map(projectMemberMapper::toProjectMemberResponse)
                .toList());

        return memberResponseList;
    }

    @Override
    public MemberResponse inviteMember(Long userId, Long projectId, InviteMemberRequest request) {
        Project project = getAccessibleProjectById(userId, projectId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("User is not the owner of the project");
        }

        User invitee = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (invitee.getId().equals(userId)) {
            throw new RuntimeException("User cannot invite themselves");
        }

        if (request.role() == ProjectRole.OWNER) {
            throw new RuntimeException("Cannot assign OWNER role to a member");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, invitee.getId());

        if (projectMemberRepository.existsById(projectMemberId)) {
            throw new RuntimeException("User is already a member of the project");
        }

        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(invitee)
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .build();

        projectMemberRepository.save(projectMember);

        return projectMemberMapper.toProjectMemberResponse(projectMember);
    }

    @Override
    public MemberResponse updateMemberRole(Long userId, Long projectId, Long memberId,
            UpdateMemberRoleRequest request) {
        Project project = getAccessibleProjectById(userId, projectId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("User is not the owner of the project");
        }

        if (memberId.equals(userId)) {
            throw new RuntimeException("Owner cannot update their own role");
        }

        if (request.role() == ProjectRole.OWNER) {
            throw new RuntimeException("Cannot assign OWNER role to a member");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);

        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new RuntimeException("Member not found in the project"));

        if (projectMember.getProjectRole() == request.role()) {
            return projectMemberMapper.toProjectMemberResponse(projectMember);
        }

        projectMember.setProjectRole(request.role());
        projectMemberRepository.save(projectMember);

        return projectMemberMapper.toProjectMemberResponse(projectMember);
    }

    @Override
    public void removeProjectMember(Long userId, Long projectId, Long memberId) {
        Project project = getAccessibleProjectById(userId, projectId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("User is not the owner of the project");
        }

        if (memberId.equals(userId)) {
            throw new RuntimeException("Owner cannot remove themselves from the project");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);

        if (!projectMemberRepository.existsById(projectMemberId)) {
            throw new RuntimeException("Member not found in the project");
        }

        projectMemberRepository.deleteById(projectMemberId);
    }

    private Project getAccessibleProjectById(Long userId, Long projectId) {
        return projectRepository.findAccessibleProjectById(userId, projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }
}
