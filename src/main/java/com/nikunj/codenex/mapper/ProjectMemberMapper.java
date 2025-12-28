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

    @Mapping(source = "id", target = "userId")
    @Mapping(target = "projectRole", constant = "OWNER")
    @Mapping(target = "invitedAt", ignore = true)
    @Mapping(target = "acceptedAt", ignore = true)
    @Mapping(target = "isAccepted", expression = "java(true)")
    MemberResponse toMemberResponseFromOwner(User owner);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "projectRole", target = "projectRole")
    @Mapping(source = "acceptedAt", target = "acceptedAt")
    @Mapping(source = "acceptedAt", target = "isAccepted", qualifiedByName = "mapIsAccepted")
    MemberResponse toProjectMemberResponse(ProjectMember projectMember);

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    @Mapping(source = "project.owner.id", target = "ownerId")
    @Mapping(source = "project.owner.name", target = "ownerName")
    @Mapping(source = "project.owner.email", target = "ownerEmail")
    @Mapping(source = "projectRole", target = "role")
    @Mapping(source = "invitedAt", target = "invitedAt")
    PendingInviteResponse toPendingInviteResponse(ProjectMember projectMember);

    @Named("mapIsAccepted")
    default Boolean mapIsAccepted(Instant acceptedAt) {
        return acceptedAt != null;
    }

}
