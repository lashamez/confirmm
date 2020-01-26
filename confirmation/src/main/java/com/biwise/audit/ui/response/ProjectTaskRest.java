package com.biwise.audit.ui.response;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProjectTaskRest {

    private UserRest assignedUser;

    private UserRest assigner;

    private ProjectRest project;

    private String task;

    private Timestamp deadline;
}
