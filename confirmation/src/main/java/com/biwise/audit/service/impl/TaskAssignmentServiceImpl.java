package com.biwise.audit.service.impl;

import com.biwise.audit.domain.dto.ProjectTaskDto;
import com.biwise.audit.domain.entity.ProjectEntity;
import com.biwise.audit.domain.entity.ProjectTaskEntity;
import com.biwise.audit.domain.entity.UserEntity;
import com.biwise.audit.repository.ProjectRepository;
import com.biwise.audit.repository.ProjectTaskRepository;
import com.biwise.audit.repository.UserRepository;
import com.biwise.audit.service.TaskAssignmentService;
import com.biwise.audit.ui.request.ProjectTaskRequestModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskAssignmentServiceImpl implements TaskAssignmentService {

    private final ProjectRepository projectRepository;

    private final ProjectTaskRepository projectTaskRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public TaskAssignmentServiceImpl(ProjectRepository projectRepository, ProjectTaskRepository projectTaskRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectTaskRepository = projectTaskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void insertTask(ProjectTaskRequestModel taskRequest, String projectId, String userId, String assignerEmail) {
        ProjectEntity projectEntity = projectRepository.findByProjectId(projectId);
        UserEntity assigned = userRepository.findByUserId(userId);
        UserEntity assigner = userRepository.findByEmail(assignerEmail);

        ProjectTaskEntity projectTaskEntity = new ProjectTaskEntity();
        projectTaskEntity.setAssignedUser(assigned);
        projectTaskEntity.setAssigner(assigner);
        projectTaskEntity.setTask(taskRequest.getTask());
        projectTaskEntity.setDeadline(new Timestamp(taskRequest.getDeadLineDate().getTime()));
        projectTaskEntity.setProject(projectEntity);

        projectTaskRepository.save(projectTaskEntity);
    }

    @Override
    public Set<ProjectTaskDto> getTasksForProject(String projectId) {
        return projectTaskRepository.findAllByProject_ProjectId(projectId).stream()
        .map(projectTaskEntity -> modelMapper.map(projectTaskEntity, ProjectTaskDto.class))
        .collect(Collectors.toSet());
    }
}
