package com.biwise.audit.service;

import com.biwise.audit.domain.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    ProjectDto createProject(ProjectDto projectDto);

    List<ProjectDto> findAll();

    ProjectDto findByProjectId(String id);

    List<ProjectDto> findAllForUser(String email);

    ProjectDto update(ProjectDto project);

    void delete(ProjectDto projectDto);
}
