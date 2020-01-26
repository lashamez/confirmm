package com.biwise.audit.repository;

import com.biwise.audit.domain.entity.AssignedProjectRoleEntity;
import com.biwise.audit.domain.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AssignedProjectRoleRepository extends JpaRepository<AssignedProjectRoleEntity, Long> {
    void deleteAllByProject_ProjectId(String projectId);

    Set<AssignedProjectRoleEntity> findAllByProject(ProjectEntity projectEntity);

    Set<AssignedProjectRoleEntity> findAllByUser_Id(Long id);
}
