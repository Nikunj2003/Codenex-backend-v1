package com.nikunj.codenex.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.nikunj.codenex.dto.project.response.ProjectResponse;
import com.nikunj.codenex.dto.project.response.ProjectSummaryResponce;
import com.nikunj.codenex.entity.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse toProjectResponse(Project project);

    List<ProjectSummaryResponce> toProjectSummaryResponceList(List<Project> projects);

}
