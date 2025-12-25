package com.nikunj.codenex.service.impl;

import com.nikunj.codenex.dto.member.request.InviteMemberRequest;
import com.nikunj.codenex.dto.member.request.UpdateMemberRoleRequest;
import com.nikunj.codenex.dto.member.response.MemberResponse;
import com.nikunj.codenex.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {

    @Override
    public List<MemberResponse> getProjectMembers(Long userId, Long projectId) {
        // TODO: Implement get members logic
        return Collections.emptyList();
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
}
