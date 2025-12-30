package com.nikunj.codenex.service.impl;

import com.nikunj.codenex.dto.member.request.InviteActionRequest;
import com.nikunj.codenex.dto.member.request.InviteMemberRequest;
import com.nikunj.codenex.dto.member.request.TransferOwnershipRequest;
import com.nikunj.codenex.dto.member.request.UpdateMemberRoleRequest;
import com.nikunj.codenex.dto.member.response.MemberResponse;
import com.nikunj.codenex.dto.member.response.PendingInviteResponse;
import com.nikunj.codenex.entity.Project;
import com.nikunj.codenex.entity.ProjectMember;
import com.nikunj.codenex.entity.ProjectMemberId;
import com.nikunj.codenex.entity.User;
import com.nikunj.codenex.enums.InviteAction;
import com.nikunj.codenex.enums.ProjectRole;
import com.nikunj.codenex.error.BadRequestException;
import com.nikunj.codenex.error.ConflictException;
import com.nikunj.codenex.error.ForbiddenException;
import com.nikunj.codenex.error.ResourceNotFoundException;
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
        getAccessibleProjectById(userId, projectId);

        return projectMemberRepository.findByProjectId(projectId).stream()
                .map(projectMemberMapper::toProjectMemberResponse)
                .toList();
    }

    @Override
    public MemberResponse inviteMember(Long userId, Long projectId, InviteMemberRequest request) {
        Project project = getAccessibleProjectById(userId, projectId);

        if (!projectMemberRepository.isOwner(projectId, userId)) {
            throw new ForbiddenException("Only the project owner can invite members");
        }

        User invitee = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.email()));

        if (invitee.getId().equals(userId)) {
            throw new BadRequestException("You cannot invite yourself to the project");
        }

        if (request.role() == ProjectRole.OWNER) {
            throw new BadRequestException("Cannot assign OWNER role to a member. Use transfer ownership instead.");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, invitee.getId());

        if (projectMemberRepository.existsById(projectMemberId)) {
            throw new ConflictException("User is already a member of this project");
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
        getAccessibleProjectById(userId, projectId);

        if (!projectMemberRepository.isOwner(projectId, userId)) {
            throw new ForbiddenException("Only the project owner can update member roles");
        }

        if (memberId.equals(userId)) {
            throw new BadRequestException("Owner cannot update their own role");
        }

        if (request.role() == ProjectRole.OWNER) {
            throw new BadRequestException("Cannot assign OWNER role directly. Use transfer ownership instead.");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);

        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectMember", memberId.toString()));

        if (projectMember.getProjectRole() == ProjectRole.OWNER) {
            throw new BadRequestException("Cannot change the owner's role. Use transfer ownership instead.");
        }

        if (projectMember.getProjectRole() == request.role()) {
            return projectMemberMapper.toProjectMemberResponse(projectMember);
        }

        projectMember.setProjectRole(request.role());
        projectMemberRepository.save(projectMember);

        return projectMemberMapper.toProjectMemberResponse(projectMember);
    }

    @Override
    public void removeProjectMember(Long userId, Long projectId, Long memberId) {
        getAccessibleProjectById(userId, projectId);

        if (!projectMemberRepository.isOwner(projectId, userId)) {
            throw new ForbiddenException("Only the project owner can remove members");
        }

        if (memberId.equals(userId)) {
            throw new BadRequestException("Owner cannot remove themselves from the project");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);

        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectMember", memberId.toString()));

        if (projectMember.getProjectRole() == ProjectRole.OWNER) {
            throw new BadRequestException("Cannot remove the project owner");
        }

        projectMemberRepository.deleteById(projectMemberId);
    }

    @Override
    public MemberResponse respondToInvite(Long userId, Long projectId, InviteActionRequest request) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, userId);

        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Invite", "No pending invite found for this project"));

        if (projectMember.getProjectRole() == ProjectRole.OWNER) {
            throw new BadRequestException("Owner cannot respond to invite");
        }

        if (projectMember.getAcceptedAt() != null) {
            throw new ConflictException("Invite has already been accepted");
        }

        if (request.action() == InviteAction.ACCEPT) {
            projectMember.setAcceptedAt(Instant.now());
            projectMemberRepository.save(projectMember);
            return projectMemberMapper.toProjectMemberResponse(projectMember);
        } else {
            projectMemberRepository.deleteById(projectMemberId);
            return null;
        }
    }

    @Override
    public List<PendingInviteResponse> getPendingInvites(Long userId) {
        return projectMemberRepository.findByUserIdAndAcceptedAtIsNull(userId).stream()
                .map(pm -> {
                    User owner = projectMemberRepository.findOwnerByProjectId(pm.getProject().getId())
                            .map(ProjectMember::getUser)
                            .orElse(null);
                    return projectMemberMapper.toPendingInviteResponse(pm, owner);
                })
                .toList();
    }

    @Override
    public void leaveProject(Long userId, Long projectId) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, userId);

        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectMember", "You are not a member of this project"));

        if (projectMember.getProjectRole() == ProjectRole.OWNER) {
            throw new BadRequestException("Owner cannot leave their own project. Transfer ownership first.");
        }

        if (projectMember.getAcceptedAt() == null) {
            throw new BadRequestException("Cannot leave a project with a pending invite. Please reject the invite instead.");
        }

        projectMemberRepository.deleteById(projectMemberId);
    }

    @Override
    public MemberResponse transferOwnership(Long userId, Long projectId, TransferOwnershipRequest request) {
        getAccessibleProjectById(userId, projectId);

        if (!projectMemberRepository.isOwner(projectId, userId)) {
            throw new ForbiddenException("Only the project owner can transfer ownership");
        }

        Long newOwnerId = request.newOwnerId();

        if (newOwnerId.equals(userId)) {
            throw new BadRequestException("You are already the owner of this project");
        }

        ProjectMemberId newOwnerMemberId = new ProjectMemberId(projectId, newOwnerId);
        ProjectMember newOwnerMember = projectMemberRepository.findById(newOwnerMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectMember", "User is not a member of this project"));

        if (newOwnerMember.getAcceptedAt() == null) {
            throw new BadRequestException("Cannot transfer ownership to a member with a pending invite");
        }

        // Get current owner membership
        ProjectMemberId currentOwnerMemberId = new ProjectMemberId(projectId, userId);
        ProjectMember currentOwnerMember = projectMemberRepository.findById(currentOwnerMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("ProjectMember", "Current owner membership not found"));

        // Transfer ownership: demote current owner to EDITOR, promote new owner to OWNER
        currentOwnerMember.setProjectRole(ProjectRole.EDITOR);
        newOwnerMember.setProjectRole(ProjectRole.OWNER);

        projectMemberRepository.save(currentOwnerMember);
        projectMemberRepository.save(newOwnerMember);

        return projectMemberMapper.toProjectMemberResponse(newOwnerMember);
    }

    private Project getAccessibleProjectById(Long userId, Long projectId) {
        return projectRepository.findAccessibleProjectById(userId, projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));
    }
}
