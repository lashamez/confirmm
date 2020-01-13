package com.biwise.audit.domain.entity;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_task")
@ToString
public class ProjectTask {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "assigned_user_id")
    private Long assignedUserId;

    @Column(name = "task_assigner_user_id")
    private Long taskAssignerUserId;

    @Column(name = "task_id")
    private Integer taskId;

}
