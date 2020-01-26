package com.biwise.audit.service.impl;

import com.biwise.audit.domain.dto.AssignedProjectRoleDto;
import com.biwise.audit.domain.dto.ProjectDto;
import com.biwise.audit.domain.dto.UserDto;
import com.biwise.audit.domain.entity.ProjectEntity;
import com.biwise.audit.repository.AssignedProjectRoleRepository;
import com.biwise.audit.repository.ProjectRepository;
import com.biwise.audit.service.ProjectService;
import com.biwise.audit.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final Utils utils;

    private final ProjectRepository projectRepository;

    private final AssignedProjectRoleRepository roleRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public ProjectServiceImpl(Utils utils, ProjectRepository projectRepository, AssignedProjectRoleRepository roleRepository) {
        this.utils = utils;
        this.projectRepository = projectRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        projectDto.setProjectId(utils.generateProjectId(30));
        ProjectEntity projectEntity = modelMapper.map(projectDto, ProjectEntity.class);
        ProjectEntity saved = projectRepository.save(projectEntity);
        return modelMapper.map(saved, ProjectDto.class);
    }

    @Override
    public List<ProjectDto> findAll() {
        List<ProjectEntity> allProjects = projectRepository.findAll();
        return allProjects.stream()
                .map(project -> modelMapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto findByProjectId(String id) {
        ProjectEntity projectEntity = projectRepository.findByProjectId(id);
        projectEntity.setUserRoles(roleRepository.findAllByProject(projectEntity));
        ProjectDto projectDto = modelMapper.map(projectEntity, ProjectDto.class);
        Set<AssignedProjectRoleDto> roleDtos = projectEntity.getUserRoles()
                .stream().map(role -> modelMapper.map(role, AssignedProjectRoleDto.class))
                .collect(Collectors.toSet());
        projectDto.setUserRoles(roleDtos);
        return projectDto;
    }

    @Override
    public List<ProjectDto> findAllForUser(UserDto userDto) {
        List<ProjectEntity> userProjects = projectRepository.findAll()
                .stream().filter(project -> project.getUserRoles().stream()
                    .anyMatch(user -> user.getUser().getId().equals(userDto.getId()))
                        || project.getCreator().getId().equals(userDto.getId()))
                .collect(Collectors.toList());
        return userProjects.stream().map(project -> modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
    }

    @Override
    public ProjectDto update(ProjectDto project) {
        //todo
        return null;
    }

    @Override
    public void delete(ProjectDto projectDto) {
        projectRepository.deleteByProjectId(projectDto.getProjectId());
    }
}
