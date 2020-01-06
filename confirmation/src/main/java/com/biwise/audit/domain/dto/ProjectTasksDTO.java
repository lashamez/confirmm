package com.biwise.audit.domain.dto;

public class ProjectTasksDTO {
    private Long id;
    private Long projectId;
    private Long assignedUserId;
    private Long taskAssignerUserId;
    private Integer taskId;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getAssignedUserId() {
        return this.assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Long getTaskAssignerUserId() {
        return this.taskAssignerUserId;
    }

    public void setTaskAssignerUserId(Long taskAssignerUserId) {
        this.taskAssignerUserId = taskAssignerUserId;
    }

    public Integer getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}
