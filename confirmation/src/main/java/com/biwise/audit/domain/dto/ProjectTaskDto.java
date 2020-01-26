package com.biwise.audit.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectTaskDto {

    private Long id;

    private UserDto assignedUser;

    private UserDto assigner;

    private ProjectDto project;

    private String task;

    private Timestamp deadline;

    @Override
    public String toString() {
        return "ProjectTaskDto{" +
                "assignedUser=" + assignedUser.getEmail() +
                ", assigner=" + assigner.getEmail() +
                ", project=" + project.getName() +
                ", task='" + task + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
