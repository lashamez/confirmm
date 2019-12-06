package com.biwise.confirmation.repository;

import com.biwise.confirmation.domain.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    ProjectEntity findByProjectId(String projectId);
    //todo
    List<ProjectEntity> findAllForUser(String email);
    void deleteByProjectId(String projectId);
}
