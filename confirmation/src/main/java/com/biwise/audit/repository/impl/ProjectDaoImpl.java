package com.biwise.audit.repository.impl;

import com.biwise.audit.domain.entity.ProjectEntity;
import com.biwise.audit.repository.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectDaoImpl implements ProjectRepository {
    @Override
    public Optional<ProjectEntity> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<ProjectEntity> findAll() {
        return null;
    }

    @Override
    public ProjectEntity save(ProjectEntity projectEntity) {
        return null;
    }

    @Override
    public ProjectEntity update(ProjectEntity projectEntity) {
        return null;
    }

    @Override
    public void delete(ProjectEntity projectEntity) {

    }

    @Override
    public ProjectEntity findByProjectId(String projectId) {
        return null;
    }

    @Override
    public void deleteByProjectId(String projectId) {

    }
}
