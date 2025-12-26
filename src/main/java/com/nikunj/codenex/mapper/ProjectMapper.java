package com.nikunj.codenex.mapper;

import org.mapstruct.Mapper;

import com.nikunj.codenex.dto.project.response.ProjectResponse;
import com.nikunj.codenex.entity.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse toProjectResponse(Project project);

}
