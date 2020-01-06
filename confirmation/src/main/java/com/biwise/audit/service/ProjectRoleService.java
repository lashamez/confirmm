package com.biwise.audit.service;

import com.biwise.audit.domain.dto.ProjectRoleDto;

public interface ProjectRoleService {
    ProjectRoleDto findByRoleName(String name);
}
