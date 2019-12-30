package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.ProjectEntity;
import com.biwise.audit.domain.entity.RoleEntity;

public interface ProjectRepository extends Dao<ProjectEntity, Long> {
    ProjectEntity findByProjectId (String projectId);
    void deleteByProjectId (String projectId);
}
