package com.nikunj.codenex.controller;

import com.nikunj.codenex.dto.member.request.InviteActionRequest;
import com.nikunj.codenex.dto.member.request.InviteMemberRequest;
import com.nikunj.codenex.dto.member.request.UpdateMemberRoleRequest;
import com.nikunj.codenex.dto.member.response.MemberResponse;
import com.nikunj.codenex.service.ProjectMemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/members")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectMemberController {

    ProjectMemberService projectMemberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers(@PathVariable Long projectId) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(projectMemberService.getProjectMembers(userId, projectId));
    }

    @PostMapping
    public ResponseEntity<MemberResponse> inviteMember(@PathVariable Long projectId,
            @RequestBody InviteMemberRequest request) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectMemberService.inviteMember(userId, projectId, request));
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMemberRole(@PathVariable Long projectId, @PathVariable Long memberId,
            @RequestBody UpdateMemberRoleRequest request) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(projectMemberService.updateMemberRole(userId, projectId, memberId, request));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long projectId, @PathVariable Long memberId) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        projectMemberService.removeProjectMember(userId, projectId, memberId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/invite-response")
    public ResponseEntity<MemberResponse> respondToInvite(@PathVariable Long projectId,
            @RequestBody InviteActionRequest request) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        MemberResponse response = projectMemberService.respondToInvite(userId, projectId, request);
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> leaveProject(@PathVariable Long projectId) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        projectMemberService.leaveProject(userId, projectId);
        return ResponseEntity.noContent().build();
    }
}
