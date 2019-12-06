package com.biwise.confirmation.service.impl;

import com.biwise.confirmation.domain.dto.ProjectDto;
import com.biwise.confirmation.domain.entity.ProjectEntity;
import com.biwise.confirmation.repository.ProjectRepository;
import com.biwise.confirmation.service.ProjectService;
import com.biwise.confirmation.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final Utils utils;
    private final ProjectRepository projectRepository;
    private ModelMapper modelMapper = new ModelMapper();
    public ProjectServiceImpl(Utils utils, ProjectRepository projectRepository) {
        this.utils = utils;
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        projectDto.setProjectId(utils.generateProjectId(30));
        ProjectEntity projectEntity = modelMapper.map(projectDto,ProjectEntity.class);
        ProjectEntity saved = projectRepository.save(projectEntity);
        return modelMapper.map(saved, ProjectDto.class);
    }

    @Override
    public List<ProjectDto> findAll() {
        List<ProjectEntity> allProjects = projectRepository.findAll();
        System.out.println(allProjects);
        return allProjects.stream()
                .map(project->modelMapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto findByProjectId(String id) {
        ProjectEntity projectEntity = projectRepository.findByProjectId(id);
        return modelMapper.map(projectEntity, ProjectDto.class);
    }

    @Override
    public List<ProjectDto> findAllForUser(String email) {
        List<ProjectEntity> userProjects = projectRepository.findAllForUser(email);
        return userProjects.stream().map(project -> modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
    }

    @Override
    public ProjectDto update(ProjectDto project) {
        ProjectEntity projectEntity = projectRepository.findByProjectId(project.getProjectId());
        projectEntity.setProjectName(project.getProjectName());
        projectEntity.setUsers(project.getUsers());
        ProjectEntity saved = projectRepository.save(projectEntity);
        return modelMapper.map(saved, ProjectDto.class);
    }

    @Override
    public void delete(ProjectDto projectDto) {
        projectRepository.deleteByProjectId(projectDto.getProjectId());
    }
}
