package com.biwise.audit.ui.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProjectRoleRest {

    private String role;

    @Override
    public String toString() {
        return "ProjectRoleRest{" +
                "role='" + role + '\'' +
                '}';
    }
}
