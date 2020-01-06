package com.biwise.audit.ui.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProjectRest {
    private String projectId;

    @JsonManagedReference
    private Set<AssignedProjectRoleRest> userRoles = new HashSet<>();

    private String name;

    private String projectType;

    private LocalDate startYear;

    private LocalDate endYear;

    private UserRest creator;

    @Override
    public String toString() {
        return "ProjectRest{" +
                "projectId='" + projectId + '\'' +
                ", userRoles=" + userRoles +
                ", name='" + name + '\'' +
                ", projectType='" + projectType + '\'' +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                ", creator=" + creator.getEmail() +
                '}';
    }
}
