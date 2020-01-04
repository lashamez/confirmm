package com.biwise.audit.ui.request;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class AssignedRole {

    private String email;

    private String role;

    protected AssignedRole() {

    }

    public AssignedRole(String email) {
        this.email = email;
    }
}
