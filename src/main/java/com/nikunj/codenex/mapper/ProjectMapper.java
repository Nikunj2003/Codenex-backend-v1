package com.nikunj.codenex.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nikunj.codenex.dto.project.response.ProjectResponse;
import com.nikunj.codenex.dto.project.response.ProjectSummaryResponce;
import com.nikunj.codenex.entity.Project;
import com.nikunj.codenex.entity.ProjectMember;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source = "project.id", target = "id")
    @Mapping(source = "project.name", target = "name")
    @Mapping(source = "project.isPublic", target = "isPublic")
    @Mapping(source = "project.createdAt", target = "createdAt")
    @Mapping(source = "project.updatedAt", target = "updatedAt")
    @Mapping(source = "ownerMember.user", target = "owner")
    ProjectResponse toProjectResponse(Project project, ProjectMember ownerMember);

    @Mapping(source = "name", target = "projectName")
    ProjectSummaryResponce toProjectSummaryResponce(Project project);

    List<ProjectSummaryResponce> toProjectSummaryResponceList(List<Project> projects);

}
