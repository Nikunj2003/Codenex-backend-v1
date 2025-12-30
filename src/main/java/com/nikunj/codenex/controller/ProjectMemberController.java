package com.nikunj.codenex.controller;

import com.nikunj.codenex.dto.member.request.InviteActionRequest;
import com.nikunj.codenex.dto.member.request.InviteMemberRequest;
import com.nikunj.codenex.dto.member.request.TransferOwnershipRequest;
import com.nikunj.codenex.dto.member.request.UpdateMemberRoleRequest;
import com.nikunj.codenex.dto.member.response.MemberResponse;
import com.nikunj.codenex.service.ProjectMemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/members")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "Project Members", description = "Project member management endpoints")
public class ProjectMemberController {

    ProjectMemberService projectMemberService;

    @Operation(summary = "Get project members", description = "Retrieve all members of a project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved members"),
        @ApiResponse(responseCode = "404", description = "Project not found",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError")))
    })
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers(@PathVariable Long projectId) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(projectMemberService.getProjectMembers(userId, projectId));
    }

    @Operation(summary = "Invite a member", description = "Invite a new member to the project (owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Member invited successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error or invalid request",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ValidationApiError"))),
        @ApiResponse(responseCode = "403", description = "Not authorized to invite members",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "404", description = "Project or user not found",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "409", description = "User is already a member",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError")))
    })
    @PostMapping
    public ResponseEntity<MemberResponse> inviteMember(@PathVariable Long projectId,
            @Valid @RequestBody InviteMemberRequest request) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectMemberService.inviteMember(userId, projectId, request));
    }

    @Operation(summary = "Update member role", description = "Update the role of a project member (owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Member role updated successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error or invalid request",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ValidationApiError"))),
        @ApiResponse(responseCode = "403", description = "Not authorized to update roles",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "404", description = "Project or member not found",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError")))
    })
    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMemberRole(@PathVariable Long projectId, @PathVariable Long memberId,
            @Valid @RequestBody UpdateMemberRoleRequest request) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(projectMemberService.updateMemberRole(userId, projectId, memberId, request));
    }

    @Operation(summary = "Remove a member", description = "Remove a member from the project (owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Member removed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request (e.g., owner trying to remove themselves)",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "403", description = "Not authorized to remove members",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "404", description = "Project or member not found",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError")))
    })
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long projectId, @PathVariable Long memberId) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        projectMemberService.removeProjectMember(userId, projectId, memberId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Respond to invite", description = "Accept or reject a project invitation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Invite accepted"),
        @ApiResponse(responseCode = "204", description = "Invite rejected"),
        @ApiResponse(responseCode = "400", description = "Invalid request",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ValidationApiError"))),
        @ApiResponse(responseCode = "404", description = "Project or invite not found",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "409", description = "Invite already accepted",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError")))
    })
    @PostMapping("/invite-response")
    public ResponseEntity<MemberResponse> respondToInvite(@PathVariable Long projectId,
            @Valid @RequestBody InviteActionRequest request) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        MemberResponse response = projectMemberService.respondToInvite(userId, projectId, request);
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Leave project", description = "Leave a project you are a member of")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully left the project"),
        @ApiResponse(responseCode = "400", description = "Invalid request (e.g., owner trying to leave or pending invite)",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "404", description = "Project not found or not a member",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError")))
    })
    @DeleteMapping("/me")
    public ResponseEntity<Void> leaveProject(@PathVariable Long projectId) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        projectMemberService.leaveProject(userId, projectId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Transfer ownership", description = "Transfer project ownership to another member (owner only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ownership transferred successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request (e.g., transferring to self or member with pending invite)",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ValidationApiError"))),
        @ApiResponse(responseCode = "403", description = "Not authorized to transfer ownership",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "404", description = "Project or member not found",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(ref = "#/components/schemas/ApiError")))
    })
    @PostMapping("/transfer-ownership")
    public ResponseEntity<MemberResponse> transferOwnership(@PathVariable Long projectId,
            @Valid @RequestBody TransferOwnershipRequest request) {
        Long userId = 1L; // TODO: Get user ID from Spring Security later
        return ResponseEntity.ok(projectMemberService.transferOwnership(userId, projectId, request));
    }
}
