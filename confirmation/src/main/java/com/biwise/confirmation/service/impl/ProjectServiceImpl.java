package com.biwise.confirmation.service.impl;

import com.biwise.confirmation.domain.dto.ProjectDto;
import com.biwise.confirmation.domain.entity.ProjectEntity;
import com.biwise.confirmation.repository.ProjectRepository;
import com.biwise.confirmation.service.ProjectService;
import com.biwise.confirmation.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    Utils utils;
    @Autowired
    ProjectRepository projectRepository;
    private ModelMapper modelMapper = new ModelMapper();
    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        projectDto.setProjectId(utils.generateProjectId(30));
        ProjectEntity projectEntity = modelMapper.map(projectDto,ProjectEntity.class);
        ProjectEntity saved = projectRepository.save(projectEntity);
        return modelMapper.map(saved, ProjectDto.class);
    }
}
