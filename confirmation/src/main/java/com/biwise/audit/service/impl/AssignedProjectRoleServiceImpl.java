package com.biwise.audit.service.impl;

import com.biwise.audit.domain.entity.AssignedProjectRoleEntity;
import com.biwise.audit.domain.entity.ProjectEntity;
import com.biwise.audit.domain.entity.ProjectRoleEntity;
import com.biwise.audit.domain.entity.UserEntity;
import com.biwise.audit.repository.AssignedProjectRoleRepository;
import com.biwise.audit.repository.ProjectRepository;
import com.biwise.audit.repository.ProjectRoleRepository;
import com.biwise.audit.repository.UserRepository;
import com.biwise.audit.service.AssignedProjectRoleService;
import com.biwise.audit.ui.request.AssignedRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignedProjectRoleServiceImpl implements AssignedProjectRoleService {

    @Autowired
    private AssignedProjectRoleRepository assignedProjectRoleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRoleRepository projectRoleRepository;

    @Override
    public void saveRoles(List<AssignedRole> assignedRoles, String projectId) {
        assignedProjectRoleRepository.deleteAllByProject_ProjectId(projectId);
        ProjectEntity projectEntity = projectRepository.findByProjectId(projectId);
        assignedRoles.forEach(role -> {
            UserEntity userEntity = userRepository.findByEmail(role.getEmail());
            ProjectRoleEntity projectRoleEntity = projectRoleRepository.findByRole(role.getRole().getRole()).get();
            AssignedProjectRoleEntity assignedProjectRoleEntity = new AssignedProjectRoleEntity();
            assignedProjectRoleEntity.setProject(projectEntity);
            assignedProjectRoleEntity.setRole(projectRoleEntity);
            assignedProjectRoleEntity.setUser(userEntity);
            assignedProjectRoleRepository.save(assignedProjectRoleEntity);
        });
    }

}
