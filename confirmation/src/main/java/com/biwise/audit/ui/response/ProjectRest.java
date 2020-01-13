package com.biwise.audit.ui.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class ProjectRest {
    private String projectId;

    @JsonManagedReference
    private Set<AssignedProjectRoleRest> userRoles = new HashSet<>();

    private String name;

    private String projectType;

    private LocalDate startYear;

    private LocalDate endYear;

    private UserRest creator;

}
