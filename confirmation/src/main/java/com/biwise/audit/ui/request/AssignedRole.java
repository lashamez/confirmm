package com.biwise.audit.ui.request;

import com.biwise.audit.ui.response.ProjectRoleRest;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

@Embeddable
@Getter
@ToString
public class AssignedRole {

    @Email(message = "Please enter valid email")
    private String email;

    private ProjectRoleRest role;

    protected AssignedRole() {

    }

    public AssignedRole(String email) {
        this.email = email;
    }

}
