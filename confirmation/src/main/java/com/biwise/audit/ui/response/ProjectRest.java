package com.biwise.audit.ui.response;

import com.biwise.audit.ui.request.AssignedRole;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectRest {
    private String projectId;

    private List<AssignedRole> users = new ArrayList<>();

    private String name;

    private String projectType;

    private LocalDate startYear;

    private LocalDate endYear;
}
