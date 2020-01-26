package com.biwise.audit.service;

import com.biwise.audit.ui.request.AssignedRole;

import java.util.List;

public interface AssignedProjectRoleService {
    void saveRoles(List<AssignedRole> assignedRoles, String projectId);
}
