package com.nikunj.codenex.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nikunj.codenex.dto.member.response.MemberResponse;
import com.nikunj.codenex.entity.ProjectMember;
import com.nikunj.codenex.entity.User;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {

    @Mapping(source = "id", target = "userId")
    @Mapping(target = "projectRole", constant = "OWNER")
    @Mapping(target = "invitedAt", ignore = true)
    MemberResponse toMemberResponseFromOwner(User owner);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "projectRole", target = "projectRole")
    MemberResponse toProjectMemberResponse(ProjectMember projectMember);

}
