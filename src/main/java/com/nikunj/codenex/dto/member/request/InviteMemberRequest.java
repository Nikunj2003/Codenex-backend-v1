package com.nikunj.codenex.dto.member.request;

import com.nikunj.codenex.enums.ProjectRole;

public record InviteMemberRequest(String email, ProjectRole role) {
}
