package com.biwise.audit.ui.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssignedProjectRoleRest {
    private UserRest user;

    private ProjectRoleRest role;

    @JsonBackReference
    private ProjectRest project;

    @Override
    public String toString() {
        return "AssignedProjectRoleRest{" +
                "user=" + user.getEmail() +
                ", role=" + role +
                ", project=" + project.getName() +
                '}';
    }
}
