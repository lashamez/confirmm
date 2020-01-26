package com.biwise.audit.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "project_task")
@Getter
@Setter
@ToString
public class ProjectTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id")
    private UserEntity assignedUser;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "task_assigner_user_id", referencedColumnName = "id")
    private UserEntity assigner;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectEntity project;

    @NotNull
    private String task;

    @NotNull
    private Timestamp deadline;
}
