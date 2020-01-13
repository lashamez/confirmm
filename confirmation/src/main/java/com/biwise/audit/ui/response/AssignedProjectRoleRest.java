package com.biwise.audit.ui.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AssignedProjectRoleRest {
    private UserRest user;

    private ProjectRoleRest role;

    @JsonBackReference
    private ProjectRest project;

}
