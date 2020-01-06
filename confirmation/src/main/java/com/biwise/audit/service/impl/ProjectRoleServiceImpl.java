package com.biwise.audit.service.impl;

import com.biwise.audit.domain.dto.ProjectRoleDto;
import com.biwise.audit.domain.entity.ProjectRoleEntity;
import com.biwise.audit.repository.ProjectRoleRepository;
import com.biwise.audit.service.ProjectRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectRoleServiceImpl implements ProjectRoleService {
    @Autowired
    private ProjectRoleRepository projectRoleRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public ProjectRoleDto findByRoleName(String name) {
        Optional<ProjectRoleEntity> projectRoleDto = projectRoleRepository.findByRole(name);
        return projectRoleDto.map(projectRoleEntity -> modelMapper.map(projectRoleEntity, ProjectRoleDto.class))
                .orElse(null);
    }
}
