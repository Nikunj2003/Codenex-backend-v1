package com.nikunj.codenex.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.nikunj.codenex.dto.member.response.MemberResponse;
import com.nikunj.codenex.dto.member.response.PendingInviteResponse;
import com.nikunj.codenex.entity.ProjectMember;
import com.nikunj.codenex.entity.User;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "projectRole", target = "projectRole")
    @Mapping(source = "acceptedAt", target = "acceptedAt")
    @Mapping(source = "acceptedAt", target = "isAccepted", qualifiedByName = "mapIsAccepted")
    MemberResponse toProjectMemberResponse(ProjectMember projectMember);

    @Mapping(source = "projectMember.project.id", target = "projectId")
    @Mapping(source = "projectMember.project.name", target = "projectName")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    @Mapping(source = "owner.email", target = "ownerEmail")
    @Mapping(source = "projectMember.projectRole", target = "role")
    @Mapping(source = "projectMember.invitedAt", target = "invitedAt")
    PendingInviteResponse toPendingInviteResponse(ProjectMember projectMember, User owner);

    @Named("mapIsAccepted")
    default Boolean mapIsAccepted(Instant acceptedAt) {
        return acceptedAt != null;
    }

}
