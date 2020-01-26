package com.biwise.audit.service;

import com.biwise.audit.domain.dto.ProjectTaskDto;
import com.biwise.audit.ui.request.ProjectTaskRequestModel;

import java.util.Set;

public interface TaskAssignmentService {

    void insertTask(ProjectTaskRequestModel taskRequest, String projectId, String userId, String assignerEmail);

    Set<ProjectTaskDto> getTasksForProject(String projectId);
}
