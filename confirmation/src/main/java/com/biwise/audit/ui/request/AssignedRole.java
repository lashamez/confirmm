package com.biwise.audit.ui.request;

import com.biwise.audit.ui.response.ProjectRoleRest;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class AssignedRole {

    private String email;

    private ProjectRoleRest role;

    protected AssignedRole() {

    }

    public AssignedRole(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AssignedRole{" +
                "email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
