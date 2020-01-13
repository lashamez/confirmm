package com.biwise.audit.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AssignedProjectRoleDto {

    private Long id;

    private UserDto user;

    private ProjectRoleDto role;

    @JsonBackReference
    private ProjectDto project;

    public AssignedProjectRoleDto(UserDto user) {
        this.user = user;
    }

    public AssignedProjectRoleDto(UserDto userDto, ProjectDto projectDto) {
        this.user = userDto;
        this.project = projectDto;
    }
}
