package com.nikunj.codenex.service;

import com.nikunj.codenex.dto.member.request.InviteMemberRequest;
import com.nikunj.codenex.dto.member.request.UpdateMemberRoleRequest;
import com.nikunj.codenex.dto.member.response.MemberResponse;
import java.util.List;

public interface ProjectMemberService {
    List<MemberResponse> getProjectMembers(Long userId, Long projectId);

    MemberResponse inviteMember(Long userId, Long projectId, InviteMemberRequest request);

    MemberResponse updateMemberRole(Long userId, Long projectId, Long memberId, UpdateMemberRoleRequest request);

    void removeProjectMember(Long userId, Long projectId, Long memberId);
}
