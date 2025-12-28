package com.nikunj.codenex.service;

import com.nikunj.codenex.dto.member.request.InviteActionRequest;
import com.nikunj.codenex.dto.member.request.InviteMemberRequest;
import com.nikunj.codenex.dto.member.request.UpdateMemberRoleRequest;
import com.nikunj.codenex.dto.member.response.MemberResponse;
import com.nikunj.codenex.dto.member.response.PendingInviteResponse;
import java.util.List;

public interface ProjectMemberService {
    List<MemberResponse> getProjectMembers(Long userId, Long projectId);

    MemberResponse inviteMember(Long userId, Long projectId, InviteMemberRequest request);

    MemberResponse updateMemberRole(Long userId, Long projectId, Long memberId, UpdateMemberRoleRequest request);

    void removeProjectMember(Long userId, Long projectId, Long memberId);

    MemberResponse respondToInvite(Long userId, Long projectId, InviteActionRequest request);

    List<PendingInviteResponse> getPendingInvites(Long userId);

    void leaveProject(Long userId, Long projectId);
}
