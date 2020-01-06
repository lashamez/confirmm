package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.AssignedProjectRoleEntity;
import com.biwise.audit.domain.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedProjectRoleRepository extends JpaRepository<AssignedProjectRoleEntity, Long> {
    void deleteAllByProject(ProjectEntity projectEntity);
}
