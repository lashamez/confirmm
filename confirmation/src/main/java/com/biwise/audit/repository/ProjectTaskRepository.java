package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.ProjectTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ProjectTaskRepository extends JpaRepository<ProjectTaskEntity, Long> {
    Set<ProjectTaskEntity> findAllByProject_ProjectId(String projectId);
}
