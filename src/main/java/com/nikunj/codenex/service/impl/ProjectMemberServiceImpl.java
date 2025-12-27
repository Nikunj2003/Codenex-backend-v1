package com.nikunj.codenex.service.impl;

import com.nikunj.codenex.dto.member.request.InviteMemberRequest;
import com.nikunj.codenex.dto.member.request.UpdateMemberRoleRequest;
import com.nikunj.codenex.dto.member.response.MemberResponse;
import com.nikunj.codenex.entity.Project;
import com.nikunj.codenex.mapper.ProjectMemberMapper;
import com.nikunj.codenex.repository.ProjectMemberRepository;
import com.nikunj.codenex.repository.ProjectRepository;
import com.nikunj.codenex.service.ProjectMemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectMemberRepository projectMemberRepository;
    ProjectMemberMapper projectMemberMapper;
    ProjectRepository projectRepository;

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
        // TODO: Implement invite member logic
        return null;
    }

    @Override
    public MemberResponse updateMemberRole(Long userId, Long projectId, Long memberId,
            UpdateMemberRoleRequest request) {
        // TODO: Implement update member role logic
        return null;
    }

    @Override
    public void deleteProjectMember(Long userId, Long projectId, Long memberId) {
        // TODO: Implement remove member logic
    }

    private Project getAccessibleProjectById(Long userId, Long projectId) {
        return projectRepository.findAccessibleProjectById(userId, projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }
}
