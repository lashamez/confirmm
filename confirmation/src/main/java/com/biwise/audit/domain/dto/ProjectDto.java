package com.biwise.audit.domain.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProjectDto {
    private Long id;

    private String projectId;

    private String name;

    private String projectType;

    private LocalDate startYear;

    private LocalDate endYear;

    @JsonManagedReference
    private Set<AssignedProjectRoleDto> userRoles = new HashSet<>();

    private UserDto creator;
}
