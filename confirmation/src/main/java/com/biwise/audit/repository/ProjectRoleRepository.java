package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.ProjectRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRoleRepository extends JpaRepository<ProjectRoleEntity, Long> {
    Optional<ProjectRoleEntity> findByRole(String role);
}
