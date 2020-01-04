package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    ProjectEntity findByProjectId(String projectId);

    //todo
    void deleteByProjectId(String projectId);
}
